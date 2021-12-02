package com.hojak99.kakaopay.application.service

import com.hojak99.kakaopay.application.port.driven.ReadUserInvestingHistoryDrivenPort
import com.hojak99.kakaopay.application.port.driving.GetUserInvestingHistoryDrivingPort
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GetUserInvestingHistoryService(
    private val readUserInvestingHistoryDrivenPort: ReadUserInvestingHistoryDrivenPort
) : GetUserInvestingHistoryDrivingPort {

    override fun getAll(userId: Int): List<UserInvestingHistoryDomain> =
        readUserInvestingHistoryDrivenPort.findAllByUserId(userId)
            .sortedByDescending { it.createdAt }
}