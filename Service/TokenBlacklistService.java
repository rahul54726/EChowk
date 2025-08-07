package com.EChowk.EChowk.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final static String BLACKLIST_PREFIX = "blacklisted_token:";

    public void blacklistToken(String token, long expirationInSeconds) {
        redisTemplate.opsForValue().set(BLACKLIST_PREFIX + token, "true", Duration.ofSeconds(expirationInSeconds));
    }

    public boolean isTokenBlacklisted(String token) {
        return redisTemplate.hasKey(BLACKLIST_PREFIX + token);
    }
}
