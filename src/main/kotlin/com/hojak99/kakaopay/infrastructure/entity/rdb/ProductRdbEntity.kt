package com.hojak99.kakaopay.infrastructure.entity.rdb

import com.hojak99.kakaopay.common.EntityDomainType
import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.domain.enums.ProductStatusType
import java.math.BigInteger
import java.time.LocalDateTime

class ProductRdbEntity(
    var id: Int,
    var status: ProductStatusType,
    val title: String,
    val totalInvestingAmount: Long,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    val completedAt: LocalDateTime?,
    var investorCount: Int,
    var remainInvestingAmount: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) : EntityDomainType<ProductDomain> {

    override fun convertDomain() = ProductDomain(
        id,
        status,
        title,
        totalInvestingAmount,
        startedAt,
        finishedAt,
        completedAt,
        investorCount,
        remainInvestingAmount,
        createdAt,
        updatedAt
    )
}