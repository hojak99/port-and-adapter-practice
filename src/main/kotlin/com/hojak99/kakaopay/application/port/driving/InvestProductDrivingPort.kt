package com.hojak99.kakaopay.application.port.driving

import com.hojak99.kakaopay.application.dto.InvestProductResultType
import java.time.LocalDateTime

interface InvestProductDrivingPort {
    fun request(
        userId: Int,
        productId: Int,
        requestAmount: Long,
        now: LocalDateTime = LocalDateTime.now()
    ): InvestProductResultType
}