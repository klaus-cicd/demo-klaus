package com.klaus.demo.caffeine.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 缓存配置
 *
 * @author klaus
 * @date 2024/01/11
 */
@Slf4j
@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                // .expireAfterAccess(1, TimeUnit.DAYS)
                // 初始的缓存空间大小
                .initialCapacity(50)
                .expireAfter(new Expiry<Object, Object>() {
                    @Override
                    public long expireAfterCreate(@NonNull Object o, @NonNull Object o2, long l) {
                        return 0;
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull Object o, @NonNull Object o2, long l, @NonNegative long l1) {
                        return 0;
                    }

                    @Override
                    public long expireAfterRead(@NonNull Object o, @NonNull Object o2, long l, @NonNegative long l1) {
                        return 0;
                    }
                })
                // 缓存的最大条数
                // .maximumSize(100);
                .build();
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().initialCapacity(50));
        return caffeineCacheManager;
    }
}
