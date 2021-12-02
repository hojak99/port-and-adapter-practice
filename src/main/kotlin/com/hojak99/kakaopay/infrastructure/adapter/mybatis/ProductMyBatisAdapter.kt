package com.hojak99.kakaopay.infrastructure.adapter.mybatis

import com.hojak99.kakaopay.application.port.driven.ReadProductDrivenPort
import com.hojak99.kakaopay.domain.ProductDomain
import com.hojak99.kakaopay.domain.enums.ProductStatusType
import com.hojak99.kakaopay.infrastructure.mapper.ProductMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class ProductMyBatisAdapter(
    private val productMapper: ProductMapper
) : ReadProductDrivenPort {

    override fun findAllByBetweenStartAndFinish(now: LocalDateTime): List<ProductDomain> =
        productMapper.findAllByBetweenStartAndFinish(now)
            .map { it.convertDomain() }

    override fun findById(productId: Int): ProductDomain =
        productMapper.findById(productId)?.convertDomain() ?: throw IllegalArgumentException("없음")

    fun updateSync(
        productDomain: ProductDomain
    ) {
        productMapper.updateSync(productDomain)
    }
}