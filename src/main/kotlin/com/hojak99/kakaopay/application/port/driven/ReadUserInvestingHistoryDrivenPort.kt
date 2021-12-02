package com.hojak99.kakaopay.application.port.driven

import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain

interface ReadUserInvestingHistoryDrivenPort {
    fun findAllByUserId(userId: Int): List<UserInvestingHistoryDomain>
}