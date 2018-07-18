# lbc
lbc(local batch cache),本地缓存框架，聚焦于批量缓存信息，为本地批量缓存提供解决方案

适用场景：

本人所经历过的项目很多时候会遇到一些字典型或者配置型的数据（比如类目信息、地域信息等等），这部分数据本身量级不是特别大，但是访问却非常频繁。为了解放应用访问外部存储所带来的压力，自己开发了lbc框架。
lbc特别适用于字典型或者配置型数据、需要缓存预加载、需要保证缓存数据与原始数据一致性、需要对本地缓存数据进行查询等等场景；
      
### 1.主要功能模块：
lbc(local batch cache),依赖于spring框架实现，提供：缓存预加载，本地缓存查询，缓存数据一致性解决方案，缓存淘汰机制等等功能

 ##### 1.1 缓存预加载
 lbc支持应用启动完成之前加载用户的热点数据，可以防止应用启动完成初始阶段缓存击穿带来的响应慢甚至雪崩问题

 ##### 1.2 支持本地缓存数据一致性
 很多项目都会涉及到字典型或者配置型数据，这种类型的数据虽然变化频率不是特别高，但是也会存在变动的情况，所以缓存数据一致性的需求是必须支持的。
 lbc支持观察者和轮询两种模式，观察者模式目前只实现了依赖zookeeper的实现，轮询模式需要用户提供获取是否需要更新状态的接口
 
 ##### 1.3 本地内存查询
 lbc封装了查询功能，支持两种查询方式，能够满足一般的查询需求，后续会扩展更多的查询功能
 
 ##### 1.4 缓存淘汰机制
 lbc目前提供了lru的缓存淘汰机制，其中用户指定预加载的热点数据不会参与到淘汰的数据中，因为这部分数据使用频率很高，没必要加入淘汰机制
 
 ##### 1.5 缓存状态信息监控及维护
 
 ##### 其他
 
 事件机制：事件机制主要用于支持lbc的可扩展性。用户可以实现自定义的监听器监听感兴趣的事件，比如缓存刷新事件，用户可以跟踪监控到缓存的相关状态信息   
 
 防缓存击穿策略：缓存击穿在lbc场景下不会频繁发生，一个是lb主要针对的是字典型数据，第二个缓存预加载已经加载了热点数据。目前采用的互斥锁方式防止缓存击穿带来的雪崩问题。
 
 防缓存穿透策略：为了防止缓存穿透带来的问题，lbc提供布隆过滤的策略
 
 本地缓存状态信息维护，计划使用jmx
 
### 2.使用说明

lbc环境要求：java8及以上，spring4.x及以上。

lbc使用项目案例可以查看：https://github.com/wufenyun/lbc-demo


(1) 通过maven引入jar包，最新版本1.2.0

```java  
  <dependency>
    <groupId>com.github.wufenyun</groupId>
    <artifactId>lbc</artifactId>
    <version>xxx</version>
  </dependency>  
```

(2) 配置并开启lbc

方法一：spring框架下适用

```java  
@Configuration
public class LocalCacheConfig {

    @Bean()
    public DefaultCacheContext config() throws Exception {
        DefaultCacheContext bean = new DefaultCacheContext();
        LbcConfiguration configuration = new LbcConfiguration.Builder()
                .lruEliminationConfig(10)
                .zkMonitorConfig("127.0.0.1:2181","rms")
                .build();
        bean.setConfiguration(configuration);

        return bean;
    }
```

方法二：springboot框架下适用

```java   
@SpringBootApplication
@EnableLbc(eliminationPolicy = EliminationConfig.EliminationPolicy.LRU,cacheSizeThreshold = 10, monitorModel = MonitorConfig.MonitorModel.EVENT_ZK, zkConnection = "127.0.0.1:2181", yourZkDataNode = "region",preventPenetrationPolicy = PreventPenetrationConfig.PreventPenetrationPolicy.BLOOM_FILTER)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

(3) 自定义CacheLoader  

自定义CacheLoader需要实现CacheLoader<K,V>接口，
K指key类型，建议使用String、Long等等基本数据类型的封装类
V指缓存对象类型。

CacheLoader.preLoadingKey()方法开放给使用者自定义预加载缓存key，可以是动态或者硬编码，任君发挥。

CacheLoader.load(Object key)方法开放给使用者自定义缓存加载，在缓存预加载已经动态加载数据到缓存时需要。

下面是一个获取地域信息实例：
```java  
@Component
public class RegionLoader implements CacheLoader<String,RegionDto> {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public List<RegionDto> load(String key) throws Exception {
        return regionMapper.listAll();
    }

    @Override
    public String preLoadingKey() {
        return "allRegion";
    }

}
```

(4) Cache对象开箱即用

Cache对象在spring应用中是全局唯一的单实例，使用者只需要在业务类中引入Cache对象就可以使用了。
对于使用者来说查询功能使用频率较高，lbc支持两种查询方式：

eg:

```java  
QueryingCollection<RegionDto> queryingCollection = localCache.get("allRegion");

//查询方式1
RegionDto example = new RegionDto();
example.setRegionId(regionId);
RegionDto regionDto = queryingCollection.queryUnique(Query.query(Example.of(regionId)));

//查询方式2
regionDto = queryingCollection.queryUnique(Query.query(Criteria.where("regionId").is(regionId)));
```

其他更多的功能请查看案例项目或者查看源码说明