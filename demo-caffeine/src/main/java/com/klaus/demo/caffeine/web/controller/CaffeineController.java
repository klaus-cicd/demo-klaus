package com.klaus.demo.caffeine.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author Klaus
 */
@Slf4j
@RestController
@RequestMapping("/api/caffeine")
public class CaffeineController {

    @Resource
    private CacheManager cacheManager;
    @Resource
    private com.github.benmanes.caffeine.cache.Cache<String, Object> caffeineCache;
    private final String A = "000";

    @Cacheable(value = "annoA", key = "'a'", cacheManager = "cacheManager")
    @GetMapping("/anno")
    public String annoGet() {
        log.info("execute annoGet");
        return this.A;
    }


    @CachePut(value = "annoA", key = "'a'", cacheManager = "cacheManager")
    @PutMapping("/anno")
    public String annoUpdate(String a) {
        log.info("execute annoUpdate");
        return a;
    }


    @CacheEvict(value = "annoA", key = "'a'", cacheManager = "cacheManager")
    @DeleteMapping("/anno")
    public void annoRemove() {
        log.info("execute annoRemove...");
    }

    @GetMapping("/all")
    public Collection<String> queryAllCache() {
        log.info("All cache name: {}", cacheManager.getCacheNames());
        return cacheManager.getCacheNames();
    }

    @GetMapping
    public String getByMethod() {
        Cache cache = cacheManager.getCache("annoA");
        return cache.get("a", String.class);
    }

    @PutMapping
    public void updateByCacheManager(String a) {
        Cache cache = cacheManager.getCache("annoA");
        cache.put("a", a);
    }

    @DeleteMapping
    public void deleteByCacheManager() {
        Cache cache = cacheManager.getCache("annoA");
        cache.evict("a");
    }

    @GetMapping("/caffeineCache")
    public String getByCaffeineCache() {
        return (String) caffeineCache.getIfPresent("a");
    }

    @PutMapping("/caffeineCache")
    public void updateByCaffeineCache(String a) {
        caffeineCache.put("a", a);
    }

    @DeleteMapping("/caffeineCache")
    public void removeByCaffeineCache(String a) {
        caffeineCache.invalidate(a);
    }
}

