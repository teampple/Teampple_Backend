package Backend.teampple.infra.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    // redis와 connection을 생성
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig =
                new RedisStandaloneConfiguration(host, 6379);

//        if (password != null && !password.isBlank()) {
//            redisConfig.setPassword(password);
//        }

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .commandTimeout(Duration.ofSeconds(1))
                        .shutdownTimeout(Duration.ZERO)
                        .build();
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
//                .master("mymaster")
//                .sentinel(host,26379)
//                .sentinel(host,26380)
//                .sentinel(host,26381);
//
//        if (password != null && !password.isBlank()) {
//            redisSentinelConfiguration.setPassword(password);
//        }
//
//        LettuceClientConfiguration clientConfig =
//                LettuceClientConfiguration.builder()
//                        .commandTimeout(Duration.ofSeconds(1))
//                        .shutdownTimeout(Duration.ZERO)
//                        .build();
//        return new LettuceConnectionFactory(redisSentinelConfiguration, clientConfig);
//    }

    // RedisConnection에서 넘겨준 byte 값을 객체 직렬화
    @Bean
    public RedisTemplate<?,?> redisTemplate(){
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        // redis-cli을 통해 직접 데이터를 보려고 할 때 알아볼 수 없는 형태로 출력 방지
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
