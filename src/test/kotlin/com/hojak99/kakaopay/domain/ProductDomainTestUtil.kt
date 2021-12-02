package com.hojak99.kakaopay.domain

import com.hojak99.kakaopay.domain.enums.ProductStatusType
import java.time.LocalDateTime
import kotlin.random.Random

object ProductDomainTestUtil {

    fun `진행 중인 투자 전 상품`(now: LocalDateTime): ProductDomain {
        val totalInvestingAmount = Random.nextInt(5000, 10000).toLong()
        return ProductDomain(
            id = Random.nextInt(),
            status = ProductStatusType.ING,
            title = "TEST",
            totalInvestingAmount = totalInvestingAmount,
            startedAt = now.minusDays(1),
            finishedAt = now.plusDays(1),
            completedAt = null,
            investorCount = 0,
            remainInvestingAmount = totalInvestingAmount,
        )
    }

    fun `진행 중인 상품`(totalAmount: Long, remainAmount: Long, now: LocalDateTime): ProductDomain =
        ProductDomain(
            id = Random.nextInt(),
            status = ProductStatusType.ING,
            title = "TEST",
            totalInvestingAmount = totalAmount,
            startedAt = now.minusDays(1),
            finishedAt = now.plusDays(1),
            completedAt = null,
            investorCount = 0,
            remainInvestingAmount = remainAmount,
        )

    fun `금액 마감`(now: LocalDateTime): ProductDomain {
        val totalInvestingAmount = Random.nextInt(5000, 10000).toLong()
        return ProductDomain(
            id = Random.nextInt(),
            status = ProductStatusType.DONE,
            title = "TEST",
            totalInvestingAmount = totalInvestingAmount,
            startedAt = now.minusDays(1),
            finishedAt = now.plusDays(1),
            completedAt = null,
            investorCount = Random.nextInt(1, 50),
            remainInvestingAmount = 0,
        )
    }

    fun `대기 상태`(now: LocalDateTime): ProductDomain {
        val totalInvestingAmount = Random.nextInt(5000, 10000).toLong()
        return ProductDomain(
            id = Random.nextInt(),
            status = ProductStatusType.WAIT,
            title = "TEST",
            totalInvestingAmount = totalInvestingAmount,
            startedAt = now.minusDays(1),
            finishedAt = now.plusDays(1),
            completedAt = null,
            investorCount = Random.nextInt(1, 50),
            remainInvestingAmount = 0,
        )
    }

    fun `종료시간 이후`(now: LocalDateTime): ProductDomain =
        ProductDomain(
            id = Random.nextInt(),
            status = ProductStatusType.DONE,
            title = "TEST",
            totalInvestingAmount = 1000,
            startedAt = now.minusDays(5),
            finishedAt = now.minusDays(1),
            completedAt = null,
            investorCount = 0,
            remainInvestingAmount = 0,
        )

}