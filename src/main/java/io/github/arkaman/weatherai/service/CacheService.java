package io.github.arkaman.weatherai.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class CacheService {

    private final StringRedisTemplate redisTemplate;

    public CacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value, long hours) {
        redisTemplate.opsForValue()
                .set(key, value, Duration.ofHours(hours));
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
