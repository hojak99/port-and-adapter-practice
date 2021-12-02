package com.hojak99.kakaopay.config

import org.redisson.codec.MarshallingCodec
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
//
//    @Bean
//    fun redisStringTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
//        val redisTemplate = RedisTemplate<String, Any>()
//        redisTemplate.setConnectionFactory(connectionFactory)
//        redisTemplate.setDefaultSerializer(StringRedisSerializer())
//        return redisTemplate
//    }
//
//    @Bean
//    fun redisIntTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, Int> {
//        val redisTemplate = RedisTemplate<String, Int>()
//        redisTemplate.setConnectionFactory(connectionFactory)
//        redisTemplate.keySerializer = StringRedisSerializer()
//        return redisTemplate
//    }

}