package com.hojak99.kakaopay.infrastructure.mapper

import com.hojak99.kakaopay.infrastructure.entity.rdb.UserInvestingHistoryRdbEntity
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface UserInvestingHistoryMapper {
    fun insert(entity: UserInvestingHistoryRdbEntity)
    fun findAllByUserId(userId: Int): List<UserInvestingHistoryRdbEntity>
}