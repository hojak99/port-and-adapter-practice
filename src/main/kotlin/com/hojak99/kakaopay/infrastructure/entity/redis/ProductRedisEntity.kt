package com.hojak99.kakaopay.infrastructure.entity.redis

import com.hojak99.kakaopay.common.EntityDomainType
import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.domain.enums.ProductStatusType
import java.time.LocalDateTime

class ProductRedisEntity(
    val id: Int,
    var status: ProductStatusType,
    val title: String,
    var totalInvestingAmount: Long,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    var completedAt: LocalDateTime?,
    var investorCount: Int,
    var remainInvestingAmount: Long,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
) : EntityDomainType<ProductDomain> {

    override fun convertDomain() =
        ProductDomain(
            id = this.id,
            status = this.status,
            title = this.title,
            totalInvestingAmount = this.totalInvestingAmount,
            startedAt = this.startedAt,
            finishedAt = this.finishedAt,
            completedAt = this.completedAt,
            remainInvestingAmount = this.remainInvestingAmount,
            investorCount = this.investorCount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )

    fun sync(productDomain: ProductDomain) {
        this.status = productDomain.status
        this.totalInvestingAmount = productDomain.totalInvestingAmount
        this.completedAt = productDomain.completedAt
        this.remainInvestingAmount = productDomain.remainInvestingAmount
        this.investorCount = productDomain.investorCount
        this.updatedAt = LocalDateTime.now()
    }

    companion object {
        fun convert(productDomain: ProductDomain) =
            ProductRedisEntity(
                id = productDomain.id,
                status = productDomain.status,
                title = productDomain.title,
                totalInvestingAmount = productDomain.totalInvestingAmount,
                startedAt = productDomain.startedAt,
                finishedAt = productDomain.finishedAt,
                completedAt = productDomain.completedAt,
                createdAt = productDomain.createdAt,
                updatedAt = productDomain.updatedAt,
                investorCount = productDomain.investorCount,
                remainInvestingAmount = productDomain.remainInvestingAmount
            )
    }
}