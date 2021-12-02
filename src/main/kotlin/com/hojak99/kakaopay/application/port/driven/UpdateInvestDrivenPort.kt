package com.hojak99.kakaopay.application.port.driven

import com.hojak99.kakaopay.application.dto.InvestProductResultType
import java.math.BigInteger

interface UpdateInvestDrivenPort {
    fun tryDecrementRemainAmount(userId: Int, productId: Int, amount: Long): InvestProductResultType
}