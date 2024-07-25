package com.practiceproject.EmployeeManagementSystem.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
// import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
// import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport{
    @Primary
    @Bean
    public CacheManager cacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> caches = new ArrayList<Cache>();
        caches.add(new ConcurrentMapCache("employees"));
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
