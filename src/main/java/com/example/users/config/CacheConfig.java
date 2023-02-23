package com.example.users.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    //  Cache local
    @Bean
    public CacheManager getCache() {
        return new ConcurrentMapCacheManager("users", "roles");
    }

/*    @Bean
    public CacheManager getCache(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        config.put("users", new CacheConfig());
        return new RedissonSpringCacheManager(redissonClient);
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }*/
}
