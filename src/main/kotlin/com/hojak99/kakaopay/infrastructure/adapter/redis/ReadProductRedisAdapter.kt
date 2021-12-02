package com.hojak99.kakaopay.infrastructure.adapter.redis

import com.hojak99.kakaopay.application.port.driven.ReadProductDrivenPort
import com.hojak99.kakaopay.common.RedisUtil
import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.infrastructure.adapter.mybatis.ProductMyBatisAdapter
import com.hojak99.kakaopay.infrastructure.entity.redis.ProductRedisEntity
import com.hojak99.kakaopay.infrastructure.entity.redis.RedisKey
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
@Primary
class ReadProductRedisAdapter(
    private val redisStringTemplate: RedisTemplate<String, String>,
    private val productMyBatisAdapter: ProductMyBatisAdapter,
) : ReadProductDrivenPort {

    override fun findAllByBetweenStartAndFinish(now: LocalDateTime): List<ProductDomain> {
        val data =
            RedisUtil.getDomainList(redisStringTemplate, RedisKey.getActiveProductKey(), ProductRedisEntity::class.java)

        if (data.isNotEmpty()) {
            return data.map { it.convertDomain() }
        }

        val domainData = productMyBatisAdapter.findAllByBetweenStartAndFinish(now)
        val target = domainData.map { ProductRedisEntity.convert(it) }
        RedisUtil.setObject(redisStringTemplate, RedisKey.getActiveProductKey(), target)
        return domainData
    }

    override fun findById(productId: Int): ProductDomain =
        RedisUtil.getDomainList(redisStringTemplate, RedisKey.getActiveProductKey(), ProductRedisEntity::class.java)
            .single { it.id == productId }.convertDomain()
}