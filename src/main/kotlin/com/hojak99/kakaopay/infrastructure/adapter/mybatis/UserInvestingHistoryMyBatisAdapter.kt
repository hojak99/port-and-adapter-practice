package com.hojak99.kakaopay.infrastructure.adapter.mybatis

import com.hojak99.kakaopay.application.port.driven.ReadUserInvestingHistoryDrivenPort
import com.hojak99.kakaopay.application.port.driven.WriteUserInvestingHistoryDrivenPort
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import com.hojak99.kakaopay.infrastructure.entity.rdb.UserInvestingHistoryRdbEntity
import com.hojak99.kakaopay.infrastructure.mapper.UserInvestingHistoryMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserInvestingHistoryMyBatisAdapter(
    val userInvestingHistoryMapper: UserInvestingHistoryMapper
) : ReadUserInvestingHistoryDrivenPort, WriteUserInvestingHistoryDrivenPort {

    override fun findAllByUserId(userId: Int): List<UserInvestingHistoryDomain> =
        userInvestingHistoryMapper.findAllByUserId(userId)
            .map { it.convertDomain() }

    override fun insert(domain: UserInvestingHistoryDomain): UserInvestingHistoryDomain {
        val entity = UserInvestingHistoryRdbEntity.create(domain)
        userInvestingHistoryMapper.insert(entity)
        return entity.convertDomain()
    }
}