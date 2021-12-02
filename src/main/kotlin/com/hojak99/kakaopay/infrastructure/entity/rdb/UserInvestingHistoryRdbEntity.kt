package com.hojak99.kakaopay.infrastructure.entity.rdb

import com.hojak99.kakaopay.common.EntityDomainType
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import java.time.LocalDateTime

class UserInvestingHistoryRdbEntity(
    var id: Int?,
    val userId: Int,
    val productId: Int,
    val title: String,
    val totalInvestingAmount: Long,
    val investingAmount: Long,
    val createdAt: LocalDateTime
) : EntityDomainType<UserInvestingHistoryDomain> {

    override fun convertDomain(): UserInvestingHistoryDomain =
        UserInvestingHistoryDomain(
            id, userId, productId, title, totalInvestingAmount, investingAmount, createdAt
        )

    companion object {
        fun create(domain: UserInvestingHistoryDomain) =
            UserInvestingHistoryRdbEntity(
                id = domain.id,
                userId = domain.userId,
                productId = domain.productId,
                investingAmount = domain.investingAmount,
                createdAt = domain.createdAt,
                totalInvestingAmount = domain.totalInvestingAmount,
                title = domain.title
            )
    }
}