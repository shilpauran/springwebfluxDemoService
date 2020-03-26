package com.sap.slh.tax.attributes.determination.springwebfluxdemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxDetails;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxLine;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.util.JsonUtil;

@Configuration
@EnableAsync
public class RedisConfig {
	private static final Logger log = LoggerFactory.getLogger(RedisConfig.class);

	@Value("${vcap.services.redis.credentials.hostname:10.11.241.101}")
	private String host;

	@Value("${vcap.services.redis.credentials.port:36516}")
	private int port;

	@Value("$vcap.services.redis.credentials.password:ErOnZmzRj2q82vd0")
	private String password;

	@Bean
	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
		redisStandaloneConfiguration.setDatabase(0);
		log.error("Redis standalone configuration{}",JsonUtil.toJsonString(redisStandaloneConfiguration));
//		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder().build();
//		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		lettuceConnectionFactory.afterPropertiesSet();
		return lettuceConnectionFactory;
		
	}
	
	@Bean
	ReactiveRedisOperations<TaxDetails, TaxLine> redisOperations(
			ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
		Jackson2JsonRedisSerializer<TaxDetails> serializer = new Jackson2JsonRedisSerializer<>(TaxDetails.class);
		Jackson2JsonRedisSerializer<TaxLine> serializer1 = new Jackson2JsonRedisSerializer<>(TaxLine.class);
		RedisSerializationContext.RedisSerializationContextBuilder<TaxDetails, TaxLine> builder = RedisSerializationContext
				.newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<TaxDetails, TaxLine> context = builder.key(serializer).value(serializer1).build();
		return new ReactiveRedisTemplate<>(
				reactiveRedisConnectionFactory, context);
	}
}
