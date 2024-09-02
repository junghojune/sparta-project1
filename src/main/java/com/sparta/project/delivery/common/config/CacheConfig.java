package com.sparta.project.delivery.common.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

        // 캐시의 기본 설정
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()  // null 값은 캐싱X
                .entryTtl(Duration.ofMinutes(10L))  // 캐시 유효 시간 설정
                .computePrefixWith(CacheKeyPrefix.simple())  // 캐시 키의 접두어를 간단하게 계산 EX) UserCacheStore::Key 의 형태로 저장
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))  // 키의 직렬화 방식 설정
                // CustomUserDetails 의 부가적인 필드들의 오류를 ignore
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer().configure(
                                objectMapper -> objectMapper.configure(
                                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
                                )
                        )
                ));  // 값의 직렬화 방식 설정

        // RedisCacheManager 리턴
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)  // Redis 연결 설정
                .cacheDefaults(redisCacheConfiguration)  // 기본 캐시 설정
                .build();  // RedisCacheManager 생성
    }
}
