package com.hojak99.kakaopay.controller.response

import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import java.time.LocalDateTime

data class UserInvestingHistoryResponse(
    val id: Int,
    val userId: Int,
    val productId: Int,
    val investingAmount: Long,
    val createdAt: LocalDateTime
) {

    companion object {
        fun from(domain: UserInvestingHistoryDomain) =
            UserInvestingHistoryResponse(
                id = domain.id!!,
                userId = domain.userId,
                productId = domain.productId,
                investingAmount = domain.investingAmount,
                createdAt = domain.createdAt
            )
    }
}