package com.hojak99.kakaopay.application.service

import com.hojak99.kakaopay.application.dto.InvestProductResultType
import com.hojak99.kakaopay.application.port.driven.ReadProductDrivenPort
import com.hojak99.kakaopay.application.port.driven.UpdateInvestDrivenPort
import com.hojak99.kakaopay.application.port.driven.WriteUserInvestingHistoryDrivenPort
import com.hojak99.kakaopay.application.port.driving.InvestProductDrivingPort
import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import com.hojak99.kakaopay.exceptions.ExceedInvestingAmountException
import com.hojak99.kakaopay.exceptions.NotAvailableProductException
import com.hojak99.kakaopay.exceptions.ProductSoldOutException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class InvestProductService(
    private val readProductDrivenPort: ReadProductDrivenPort,
    private val updateInvestDrivenPort: UpdateInvestDrivenPort,
    private val writeUserInvestingHistoryDrivenPort: WriteUserInvestingHistoryDrivenPort
) : InvestProductDrivingPort {

    private companion object {
        val logger = KotlinLogging.logger {}
    }

    override fun request(
        userId: Int, productId: Int, requestAmount: Long, now: LocalDateTime
    ): InvestProductResultType =
        try {
            logger.info {
                "[InvestProductService.request] try investing." +
                        "userId: $userId, productId: $productId, amount: $requestAmount"
            }
            val product = checkAvailableProduct(userId, productId, requestAmount, now)
            val result = tryDecrement(userId, productId, requestAmount)
            tryInsertHistory(userId, product, requestAmount)
            logger.info {
                "[UpdateInvestRedisAdapter.tryDecrementRemainAmount] success investing." +
                        "userId: $userId, productId: $productId, amount: $requestAmount"
            }
            result
        } catch (e: ProductSoldOutException) {
            InvestProductResultType.SOLD_OUT
        }

    private fun checkAvailableProduct(
        userId: Int,
        productId: Int,
        investingAmount: Long,
        now: LocalDateTime
    ): ProductDomain {
        val product = readProductDrivenPort.findById(productId)

        if (!product.status.isAvailable() || !product.isAvailableDate(now)) {
            logger.warn { "[UpdateInvestRedisAdapter.checkAvailableProduct] not avail product($productId). userId: $userId, request amount: $investingAmount" }
            throw NotAvailableProductException()
        }

        if (!product.isAvailableRemainAmount(investingAmount)) {
            logger.warn { "[UpdateInvestRedisAdapter.checkAvailableProduct] exceed investing amount. productId: $productId. userId: $userId, request amount: $investingAmount" }
            throw ExceedInvestingAmountException()
        }
        return product
    }

    private fun tryDecrement(userId: Int, productId: Int, requestAmount: Long): InvestProductResultType =
        try {
            val type = updateInvestDrivenPort.tryDecrementRemainAmount(
                userId,
                productId,
                requestAmount
            )
            when (type) {
                InvestProductResultType.SOLD_OUT -> throw ProductSoldOutException()
                InvestProductResultType.EXCEED -> throw ExceedInvestingAmountException()
                else -> {
                }
            }
            type
        } catch (e: Exception) {
            logger.error(e) {
                "[InvestProductService.tryDecrement] fail invest. " +
                        "userId: $userId, productId: $productId, amount: $requestAmount"
            }
            throw e
        }

    private fun tryInsertHistory(userId: Int, product: ProductDomain, requestAmount: Long) {
        try {
            writeUserInvestingHistoryDrivenPort.insert(
                UserInvestingHistoryDomain.create(userId, requestAmount, product)
            )
        } catch (e: Exception) {
            logger.error(e) {
                "[InvestProductService.tryInsertHistory] fail insert history. " +
                        "userId: $userId, productId: ${product.id}, amount: $requestAmount"
            }
            throw e
        }
    }
}