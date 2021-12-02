package com.hojak99.kakaopay.application.port.driving

import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain

interface GetUserInvestingHistoryDrivingPort {
    fun getAll(userId: Int): List<UserInvestingHistoryDomain>
}