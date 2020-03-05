package com.genius.coder.education.redis;

import com.genius.coder.education.properties.TokenRedisProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class TokenStoreConfiguration {

    @Autowired(required = false)
    private TokenRedisProperties tokenRedisProperties;

    @Bean
    public TokenStore tokenStore() {
        if (tokenRedisProperties == null) {
            return new InMemoryTokenStore();
        }
        GenericObjectPoolConfig<Object> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(20);
        LettucePoolingClientConfiguration build = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(tokenRedisProperties.getHost(), tokenRedisProperties.getPort());
        redisStandaloneConfiguration.setPassword(tokenRedisProperties.getPassword());
        redisStandaloneConfiguration.setDatabase(tokenRedisProperties.getDatabase());
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration, build);
        connectionFactory.afterPropertiesSet();
        return new EducationRedisTokenStore(connectionFactory);
    }
}
