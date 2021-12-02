package com.hojak99.kakaopay.infrastructure.adapter.redis

import com.hojak99.kakaopay.application.dto.InvestProductResultType
import com.hojak99.kakaopay.application.port.driven.UpdateInvestDrivenPort
import com.hojak99.kakaopay.exceptions.ExceedInvestingAmountException
import com.hojak99.kakaopay.exceptions.ProductSoldOutException
import com.hojak99.kakaopay.infrastructure.entity.rdb.UserInvestingHistoryRdbEntity
import com.hojak99.kakaopay.infrastructure.mapper.UserInvestingHistoryMapper
import mu.KotlinLogging
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class UpdateInvestRedisAdapter(
    private val redisClient: RedissonClient,
    private val readProductRedisAdapter: ReadProductRedisAdapter,
    private val writeProductRedisAdapter: WriteProductRedisAdapter,
) : UpdateInvestDrivenPort {

    private companion object {
        val logger = KotlinLogging.logger {}
        const val LOCK_NAME = "invest_user_lock"
    }

    override fun tryDecrementRemainAmount(
        userId: Int,
        productId: Int,
        amount: Long
    ): InvestProductResultType {
        val lock = redisClient.getLock(LOCK_NAME)
        val isLocked = lock.tryLock(3, 1, TimeUnit.SECONDS)
        if (!isLocked) {
            logger.error { "failed try lock. userId: $userId, productId: $productId" }
            throw IllegalStateException("다시 요청해주세요.")
        }
        try {
            val product = readProductRedisAdapter.findById(productId)

            if (product.isZeroRemainInvestingAmount()) {
                return InvestProductResultType.SOLD_OUT
            }
            if (!product.canInvest(amount)) {
                logger.error {
                    "[UpdateInvestingRedisAdapter.tryDecrementRemainAmount] exceed investing amount. productId: $productId " +
                            "userId: $userId, request amount: $amount, remainAmount: ${product.remainInvestingAmount}"
                }
                return InvestProductResultType.EXCEED
            }
            product.incrementInvestorCount()
            product.decrementRemainInvestingAmount(amount)
            product.checkComplete(LocalDateTime.now())

            writeProductRedisAdapter.update(product)
            return InvestProductResultType.SUCCESS
        } finally {
            lock.unlock()
        }
    }
}