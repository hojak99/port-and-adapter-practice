package com.hojak99.kakaopay.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.hojak99.kakaopay.config.CustomLocalDateTimeDeserializerConverter
import com.hojak99.kakaopay.config.CustomLocalDateTimeSerializerConverter
import org.redisson.api.RedissonClient
import org.springframework.dao.DataAccessException
import org.springframework.data.redis.core.RedisOperations
import org.springframework.data.redis.core.SessionCallback
import java.math.BigInteger
import java.time.LocalDateTime

object RedisUtil {

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(
            SimpleModule()
                .addSerializer(LocalDateTime::class.java, CustomLocalDateTimeSerializerConverter())
                .addDeserializer(LocalDateTime::class.java, CustomLocalDateTimeDeserializerConverter())
        )!!

    fun <T> transaction(
        operations: RedisOperations<String, T>,
        command: (operation: RedisOperations<String, T>) -> Unit
    ) {
        operations.execute(object : SessionCallback<Void?> {
            @Throws(DataAccessException::class)
            override fun <K : Any?, V : Any?> execute(cbOperations: RedisOperations<K, V>): Void? {
                cbOperations.multi()
                command.invoke(operations)
                cbOperations.exec()
                return null
            }
        })
    }

    @Throws(DataAccessException::class)
    fun <T> getDomain(operations: RedisOperations<String, String>, key: String, classType: Class<T>): T? {
        val json = operations.opsForValue().get(key)
        return if (json.isNullOrBlank()) {
            null
        } else {
            objectMapper.readValue(json, classType)
        }
    }

    @Throws(DataAccessException::class)
    fun <T> getDomainList(
        operations: RedisOperations<String, String>,
        key: String,
        classType: Class<T>
    ): Collection<T> {
        val json = operations.opsForValue().get(key) as String?
        return if (json.isNullOrBlank()) {
            emptyList()
        } else {
            val typeFactory = TypeFactory.defaultInstance()
            objectMapper.readValue(json, typeFactory.constructCollectionType(ArrayList::class.java, classType))
        }
    }

    @Throws(DataAccessException::class)
    fun setObject(operations: RedisOperations<String, String>, key: String, value: Any) {
        val json = objectMapper.writeValueAsString(value)
        operations.opsForValue().set(key, json)
    }

    @Throws(DataAccessException::class)
    fun setLong(operations: RedisOperations<String, String>, key: String, value: Long) {
        operations.opsForValue().set(key, value.toString())
    }

    fun getLong(operations: RedisOperations<String, String>, key: String) = operations.opsForValue().get(key)?.toLong()

    @Throws(DataAccessException::class)
    fun addSet(operations: RedisOperations<String, String>, key: String, value: Any) {
        val json = objectMapper.writeValueAsString(value)
        operations.opsForSet().add(key, json)
    }

    fun decrement(operations: RedisOperations<String, String>, key: String, value: Long) =
        operations.opsForValue().decrement(key, value)


    fun increment(operations: RedisOperations<String, String>, key: String, value: Long) {
        operations.opsForValue().increment(key, value)
    }

    fun increment(operations: RedisOperations<String, String>, key: String) {
        operations.opsForValue().increment(key)
    }

}