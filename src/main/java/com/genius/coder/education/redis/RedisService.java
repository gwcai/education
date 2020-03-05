package com.genius.coder.education.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * redis
 * @author GaoWeicai.(lili14520 @ gmail.com)
 * @date 2020/3/5
 */
@Component
@ConditionalOnProperty(prefix = "spring.redis", name = {"host", "port"})
@Slf4j
public class RedisService {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisService(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public RedisConnection getRedisConnection() {
        return redisConnectionFactory.getConnection();
    }

    /**
     * 缓存key value,设置120秒过期时间
     *
     * @return 参考setex命令
     */
    public Boolean setex(String key, String value) {
        return setex(key, value, 300L);
    }

    /**
     * 缓存key value,设置120秒过期时间
     *
     * @param exSeconds key过期时间，单位为秒
     * @return 参考setex命令
     */
    public Boolean setex(String key, String value, long exSeconds) {
        RedisConnection redisConnection = getRedisConnection();
        Boolean result = redisConnection.setEx(key.getBytes(StandardCharsets.UTF_8), exSeconds, value.getBytes(StandardCharsets.UTF_8));
        redisConnection.close();
        return result;
    }

    public String get(String key) {
        RedisConnection redisConnection = getRedisConnection();
        byte[] bytes = redisConnection.get(key.getBytes(StandardCharsets.UTF_8));
        redisConnection.close();
        if (bytes != null) {
            return new String(bytes, StandardCharsets.UTF_8);
        }
        return null;
    }

}
