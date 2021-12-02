package com.hojak99.kakaopay.application.port.driven

import com.hojak99.kakaopay.domain.ProductDomain
import java.time.LocalDateTime

interface ReadProductDrivenPort {
    fun findAllByBetweenStartAndFinish(now: LocalDateTime): List<ProductDomain>
    fun findById(productId: Int): ProductDomain
}