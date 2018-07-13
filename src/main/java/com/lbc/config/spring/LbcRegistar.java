package com.lbc.config.spring;

import com.lbc.config.LbcConfiguration;
import com.lbc.config.EliminationConfig;
import com.lbc.config.MonitorConfig;
import com.lbc.config.PreventPenetrationConfig;
import com.lbc.context.DefaultCacheContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @description:SpringBoot注解配置自动开启本地缓存
 *
 * @author: wufenyun
 * @date: 2018-07-11 17
 **/
public class LbcRegistar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableLbc.class.getName(),false);

        MonitorConfig.MonitorModel monitorModel = (MonitorConfig.MonitorModel) attributes.get("monitorModel");
        int refreshThreads = (int) attributes.get("refreshThreads");
        String zkConnection = (String) attributes.get("zkConnection");
        String yourZkDataNode = (String) attributes.get("yourZkDataNode");

        long initialDelay = (long) attributes.get("initialDelay");
        long intervalMills = (long) attributes.get("intervalMills");

        EliminationConfig.EliminationPolicy eliminationPolicy = (EliminationConfig.EliminationPolicy) attributes.get("eliminationPolicy");
        int cacheSizeThreshold = (int) attributes.get("cacheSizeThreshold");

        PreventPenetrationConfig.PreventPenetrationPolicy preventPenetrationPolicy = (PreventPenetrationConfig.PreventPenetrationPolicy) attributes.get("preventPenetrationPolicy");
        int clearIntervalSeconds = (int) attributes.get("clearIntervalSeconds");
        int expectedInsertions = (int) attributes.get("expectedInsertions");

        LbcConfiguration.Builder configBuilder = new LbcConfiguration.Builder();
        if(MonitorConfig.MonitorModel.EVENT_ZK.equals(monitorModel)) {
            configBuilder.zkMonitorConfig(refreshThreads,zkConnection,yourZkDataNode);
        } else if(MonitorConfig.MonitorModel.POLLING.equals(monitorModel)) {
            configBuilder.pollingMonitorConfig(refreshThreads,initialDelay,intervalMills);
        }

        if(EliminationConfig.EliminationPolicy.LRU.equals(eliminationPolicy)) {
            configBuilder.lruEliminationConfig(cacheSizeThreshold);
        } else if(EliminationConfig.EliminationPolicy.LFU.equals(eliminationPolicy)) {
            configBuilder.eliminationConfig(cacheSizeThreshold,EliminationConfig.EliminationPolicy.LFU);
        }

        if(PreventPenetrationConfig.PreventPenetrationPolicy.BLOOM_FILTER.equals(preventPenetrationPolicy)) {
            configBuilder.bloomPreventPenetrationConfig(clearIntervalSeconds,expectedInsertions);
        }

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DefaultCacheContext.class);
        builder.setScope(BeanDefinition.SCOPE_SINGLETON);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        builder.addPropertyValue("configuration", configBuilder.build());
        registry.registerBeanDefinition("defaultCacheContext", builder.getBeanDefinition());
    }
}