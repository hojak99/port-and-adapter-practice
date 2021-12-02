package com.hojak99.kakaopay.infrastructure.mapper

import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.domain.enums.ProductStatusType
import com.hojak99.kakaopay.infrastructure.entity.rdb.ProductRdbEntity
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.time.LocalDateTime

@Mapper
@Component
interface ProductMapper {
    fun findById(productId: Int): ProductRdbEntity?
    fun findAllByBetweenStartAndFinish(now: LocalDateTime): List<ProductRdbEntity>
    fun updateSync(productDomain: ProductDomain)
}