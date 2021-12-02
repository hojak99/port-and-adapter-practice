package com.hojak99.kakaopay.infrastructure.adapter.redis

import com.hojak99.kakaopay.common.RedisUtil
import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.infrastructure.adapter.mybatis.ProductMyBatisAdapter
import com.hojak99.kakaopay.infrastructure.entity.redis.ProductRedisEntity
import com.hojak99.kakaopay.infrastructure.entity.redis.RedisKey
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class WriteProductRedisAdapter(
    private val productMyBatisAdapter: ProductMyBatisAdapter,
    private val redisTemplate: RedisTemplate<String, String>,
) {
    fun update(productDomain: ProductDomain) {
        val data =
            RedisUtil.getDomainList(redisTemplate, RedisKey.getActiveProductKey(), ProductRedisEntity::class.java)
        data.forEach {
            if (it.id == productDomain.id) it.sync(productDomain)
        }
        RedisUtil.setObject(redisTemplate, RedisKey.getActiveProductKey(), data)

        productMyBatisAdapter.updateSync(productDomain)
    }
}