package com.hojak99.kakaopay.domain

import com.hojak99.kakaopay.domain.enums.ProductStatusType
import java.time.LocalDateTime

data class ProductDomain(
    var id: Int,
    var status: ProductStatusType,
    val title: String,
    val totalInvestingAmount: Long,
    val startedAt: LocalDateTime,
    val finishedAt: LocalDateTime,
    var completedAt: LocalDateTime?,
    var investorCount: Int,
    var remainInvestingAmount: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {

    fun isZeroRemainInvestingAmount() = this.remainInvestingAmount == 0L

    fun canInvest(request: Long) = this.remainInvestingAmount >= request

    fun incrementInvestorCount() {
        this.investorCount += 1
    }

    fun checkComplete(now: LocalDateTime) {
        if (this.remainInvestingAmount <= 0L) {
            this.completedAt = now
            this.status = ProductStatusType.DONE
        }
    }

    fun decrementRemainInvestingAmount(request: Long) {
        this.remainInvestingAmount -= request
    }

    fun isAvailableDate(now: LocalDateTime) =
        now in this.startedAt..this.finishedAt

    fun isAvailableRemainAmount(investingAmount: Long) =
        this.remainInvestingAmount >= investingAmount
}