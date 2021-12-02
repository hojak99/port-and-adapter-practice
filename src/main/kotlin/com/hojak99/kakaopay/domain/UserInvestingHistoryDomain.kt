package com.hojak99.kakaopay.domain

import java.time.LocalDateTime

data class UserInvestingHistoryDomain(
    var id: Int? = null,
    val userId: Int,
    val productId: Int,
    val title: String,
    val totalInvestingAmount: Long,
    val investingAmount: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun create(
            userId: Int,
            requestAmount: Long,
            product: ProductDomain
        ) =
            UserInvestingHistoryDomain(
                userId = userId,
                productId = product.id,
                investingAmount = requestAmount,
                title = product.title,
                totalInvestingAmount = product.totalInvestingAmount,
            )
    }
}