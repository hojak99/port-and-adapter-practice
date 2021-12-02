package com.hojak99.kakaopay.controller.response

import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.domain.enums.ProductStatusType
import java.math.BigInteger
import java.time.LocalDateTime

data class ProductResponse(
    val id: Int,
    val title: String,
    val totalInvestingAmount: Long,
    val currentInvestingAmount: Long,
    val investorCount: Int,
    val status: ProductStatusType,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime
) {
    companion object {
        fun from(productDomain: ProductDomain) =
            ProductResponse(
                id = productDomain.id,
                title = productDomain.title,
                totalInvestingAmount = productDomain.totalInvestingAmount,
                startedAt = productDomain.startedAt,
                finishedAt = productDomain.finishedAt,
                investorCount = productDomain.investorCount,
                currentInvestingAmount = productDomain.totalInvestingAmount - productDomain.remainInvestingAmount,
                status = productDomain.status
            )
    }
}