package com.sap.slh.tax.attributes.determination.springwebfluxdemo.config;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxDetails;
import com.sap.slh.tax.attributes.determination.springwebfluxdemo.model.TaxLine;

import io.lettuce.core.RedisURI;
import io.pivotal.cfenv.core.CfEnv;

@Configuration
public class RedisConfig {
	
	CfEnv cfEnv = new CfEnv();
    String tag = "redis";
    String redisHost = cfEnv.findCredentialsByTag(tag).getHost();

	@Bean
	@Primary
	public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory(RedisConfiguration defaultRedisConfig) {
		LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
				.commandTimeout(Duration.ofMillis(60000)).build();
		return new LettuceConnectionFactory(defaultRedisConfig, clientConfig);
	}

	@Bean
	public RedisConfiguration defaultRedisConfig() {
		if (redisHost != null) {
//			RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("127.0.0.1", 6379);
			RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
			String redisPort = cfEnv.findCredentialsByTag(tag).getPort();
			String redisPassword = cfEnv.findCredentialsByTag(tag).getPassword();
			config.setHostName(redisHost);
			config.setPassword(RedisPassword.of(redisPassword));
			config.setPort(Integer.parseInt(redisPort));
			config.setDatabase(2);
			return config;
		} else {
			RedisSentinelConfiguration config = new RedisSentinelConfiguration();
			String uri = cfEnv.findCredentialsByTag(tag).getUri();
			RedisURI redisURI = RedisURI.create(uri);
			config.master(redisURI.getSentinelMasterId());
			List<RedisNode> nodes = redisURI.getSentinels().stream()
					.map(redisUri -> populateNode(redisUri.getHost(), redisUri.getPort())).collect(Collectors.toList());
			nodes.forEach(node -> config.addSentinel(node));
			config.setPassword(RedisPassword.of(redisURI.getPassword()));
			config.setDatabase(2);
			return config;
		}
	}

    @Bean
    public ReactiveRedisOperations<TaxDetails, TaxLine> reactiveRedisTemplate(
        ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<TaxLine> valueSerializer = new Jackson2JsonRedisSerializer<>(
            TaxLine.class);
        Jackson2JsonRedisSerializer<TaxDetails> valueSerializer1 = new Jackson2JsonRedisSerializer<>(
                TaxDetails.class);
        RedisSerializationContext.RedisSerializationContextBuilder<TaxDetails, TaxLine> builder = RedisSerializationContext
            .newSerializationContext(keySerializer);
        RedisSerializationContext<TaxDetails, TaxLine> context = builder.key(valueSerializer1).value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
    
	private RedisNode populateNode(String host, Integer port) {
		return new RedisNode(host, port);
	}

}
