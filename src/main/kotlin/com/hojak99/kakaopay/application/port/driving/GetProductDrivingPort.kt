package com.hojak99.kakaopay.application.port.driving

import com.hojak99.kakaopay.domain.ProductDomain
import java.time.LocalDateTime

interface GetProductDrivingPort {
    fun getAllByBetween(now: LocalDateTime): List<ProductDomain>
}