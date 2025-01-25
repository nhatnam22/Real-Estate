package com.project.java.config;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.project.java.responses.address.ProvinceResponse;



@Configuration
public class RedisConfig {
	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
		return new LettuceConnectionFactory(configuration);
	}

	@Bean
	public RedisTemplate<String, ProvinceResponse> redisTemplate() {
	    RedisTemplate<String, ProvinceResponse> template = new RedisTemplate<>();
	    template.setConnectionFactory(redisConnectionFactory());
	    template.setKeySerializer(new StringRedisSerializer());
	    template.setValueSerializer(new Jackson2JsonRedisSerializer<>(ProvinceResponse.class));
	    template.setHashKeySerializer(new StringRedisSerializer());
	    template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(ProvinceResponse.class));
	    template.afterPropertiesSet();
	    return template;
	}


	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)); // TTL mặc định: 1 giờ

		RedisCacheConfiguration provincesCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofDays(1)); // TTL cho cache "provincesCache": 1 ngày

		return RedisCacheManager.builder(redisConnectionFactory)
				.withCacheConfiguration("provincesCache", provincesCacheConfig).cacheDefaults(defaultConfig).build();
	}

}
