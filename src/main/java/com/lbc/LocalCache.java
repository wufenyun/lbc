package com.lbc;

import com.lbc.config.EliminationConfig;
import com.lbc.config.LbcConfiguration;
import com.lbc.config.PreventPenetrationConfig;
import com.lbc.context.CacheContext;
import com.lbc.context.event.Event;
import com.lbc.context.event.EventFactory;
import com.lbc.context.event.EventMulticaster;
import com.lbc.maintenance.ExpiredBloomFilter;
import com.lbc.support.AssertUtil;
import com.lbc.support.CommonUtil;
import com.lbc.wrap.QueryingCollection;
import com.lbc.wrap.SimpleQueryingCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Cache接口的实现，LocalCache对使用者透明，开箱即用，生命周期由{@link com.lbc.context.DefaultCacheContext}维护。
 *
 * LocalCache使用线程安全的ConcurrentHashMap实现。
 * 维护allKeyMap,记录每个key对应的CacheLoader，当需要从外部加载数据的时候需要用到key的CacheLoader；
 * 维护cacheLoaderMapping，调用get(K key,Class<? extends CacheLoader<K, V>> clazz)方法时获取CacheLoader实例；
 *
 * get(K key,Class<? extends CacheLoader<K, V>> clazz)方法根据使用者配置做了防缓存击穿和防缓存穿透策略
 *
 * @author wufenyun
 */
public class LocalCache implements Cache {
    
    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    private Map<Object, QueryingCollection<?>> storage = new ConcurrentHashMap<>();
    private Map<Object, CacheLoader<?,?>> allKeyMap = new ConcurrentHashMap<>();
    private Map<Class<?>, CacheLoader<?, ?>> cacheLoaderMapping = new ConcurrentHashMap<>();

    private LbcConfiguration config;
    private CacheContext context;
    private LruSupport lruLinkedSupport;
    private ExpiredBloomFilter expiredBloomFilter;

    public LocalCache(CacheContext context) {
        this.context = context;
        this.config = context.getConfiguration();

        if(isLruPolicy()) {
            lruLinkedSupport = new LruSupport(config.getEliminationConfig().getCacheSizeThreshold());
        }

        if(isBloomPolicy()) {
            expiredBloomFilter = new ExpiredBloomFilter(1000,60);
        }
    }

    /**
     * 是否开启了lru缓存淘汰策略，目前只实现了了lru策略
     *
     * @return
     */
    private boolean isLruPolicy() {
        EliminationConfig eliminationConfig = config.getEliminationConfig();
        return (eliminationConfig != null)
                && EliminationConfig.EliminationPolicy.LRU.equals(eliminationConfig.getEliminationPolicy());
    }

    /**
     * 是否开了布隆过滤
     *
     * @return
     */
    private boolean isBloomPolicy() {
        PreventPenetrationConfig ppConfig = config.getPreventPenetrationConfig();
        return (null != ppConfig) &&
                PreventPenetrationConfig.PreventPenetrationPolicy.BLOOM_FILTER.equals(ppConfig.getPolicy());
    }

    @Override
    public <K,V> void put(K key, List<V> data,CacheLoader<K, V> cacheLoader) {
        QueryingCollection<V> wrapper = new SimpleQueryingCollection<>(data);
        storage.put(key, wrapper);
        allKeyMap.put(key, cacheLoader);

        lruIfNecessary(key);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public <K, V> QueryingCollection<V> refresh(K key) {
        AssertUtil.notNull(key, "key can't be null");
        CacheLoader cacheLoader = allKeyMap.get(key);
        AssertUtil.notNull(cacheLoader,"can't get the key:"+key+"'s CacheLoader");

        if(!context.getGlobalSingleCache().contains(key)) {
            logger.info("lbc have not load key：{} data，don't refresh",key);
            return null;
        }

        try {
            //这里锁住cacheLoader即只会在key的粒度上锁，应该不会有性能问题
            synchronized (cacheLoader) {
                long existedSize = getExistedSize(key);
                List<V> data = cacheLoader.load(key);
                QueryingCollection<V> wrapper = new SimpleQueryingCollection<>(data);
                Event event = EventFactory.newRefreshedEvent(key, existedSize, data.size());
                getMultiCaster().multicast(event);

                logger.debug("finished refresh the {}'s data,data: {}",key,data);
                return (QueryingCollection<V>) storage.replace(key, wrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException("刷新缓存"+key+"出现异常",e);
        }
    }

    private long getExistedSize(Object key) {
        QueryingCollection collection = context.getGlobalSingleCache().get(key);
        return (collection==null?0:collection.size());
    }

    private EventMulticaster getMultiCaster() {
        return context.getEventMulticaster();
    }

    @Override
    public <K, V> void replace(K key, List<V> value) {
        QueryingCollection<V> wrapper = new SimpleQueryingCollection<>(value);
        storage.replace(key, wrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> QueryingCollection<V> get(K key) {
        AssertUtil.notNull(key, "key不能为空");
        return doLocalGet(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, V> QueryingCollection<V> get(K key,Class<? extends CacheLoader<K, V>> clazz) {
        AssertUtil.notNull(key, "key不能为空");

        QueryingCollection<?> value = doLocalGet(key);
        if(null != value) {
            return (QueryingCollection<V>) value;
        }

        if(isBloomPolicy() && expiredBloomFilter.contains(key.toString())) {
            return null;
        }

        CacheLoader<K, V> cacheLoader = (CacheLoader<K, V>) cacheLoaderMapping.get(clazz);
        AssertUtil.notNull(key, "无法通过"+clazz+"找到其实例对象，请确保相应CacheLoader已经实例化");
        synchronized (key) {
            if(null == storage.get(key)) {
                List<V> data = null;
                try {
                    data = cacheLoader.load(key);
                } catch (Exception e) {
                    logger.error("加载数据到缓存失败，key:{}",key,e);
                    return null;
                }
                //如果为空，并且开启了布隆过滤器
                if(CommonUtil.isEmpty(data) && isBloomPolicy()) {
                    expiredBloomFilter.add(key.toString());
                    return null;
                }
                this.put(key,data,cacheLoader);
            }
        }
        
        return doLocalGet(key);
    }

    /**
     * 执行本地缓存查询，即使本地缓存中未查询到也不会从外部资源加载数据；
     * 如果开启了缓存淘汰策略则进行相关操作；
     *
     * @param key
     * @param <K>
     * @param <V>
     * @return
     */
    private <K, V> QueryingCollection<V> doLocalGet(K key) {
        QueryingCollection<V> collection = (QueryingCollection<V>) storage.get(key);

        if(null != collection) {
            lruIfNecessary(key);
        }

        return collection;
    }

    /**
     * 获取节点、载入节点之后进行lru操作
     *
     * @param key
     */
    private void lruIfNecessary(Object key) {
        //如果不需要lru处理，直接返回
        if(!isLruPolicy()) {
            return;
        }

        synchronized(key) {
            Object evictKey = lruLinkedSupport.getNeedReclaimedKey(key);
            if(null != evictKey) {
                storage.remove(evictKey);
                allKeyMap.remove(evictKey);
                logger.info("从缓存里移除LRU缓存,key={}",evictKey);
            }
        }
    }

    @Override
    public <K> boolean contains(K key) {
        return storage.containsKey(key);
    }


    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, CacheLoader<?,?>> getAllKeyMap() {
        return allKeyMap;
    }

    /**
     * 注册缓存加载器信息
     *
     * @param cacheLoader
     */
    public void registerCacheLoader(Object key, CacheLoader<?, ?> cacheLoader) {
        logger.info("registerCacheLoader caching loader that needs to be initially loaded：{}", cacheLoader.getClass());
        //添加缓存预加载信息
        allKeyMap.put(key,cacheLoader);
        //添加缓存加载器信息
        cacheLoaderMapping.put(cacheLoader.getClass(), cacheLoader);
    }

}
