package com.hojak99.kakaopay.application.service

import com.hojak99.kakaopay.application.port.driven.ReadProductDrivenPort
import com.hojak99.kakaopay.application.port.driving.GetProductDrivingPort
import com.hojak99.kakaopay.domain.ProductDomain
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class GetProductService(
    private val readProductDrivenPort: ReadProductDrivenPort,
) : GetProductDrivingPort {

    override fun getAllByBetween(now: LocalDateTime): List<ProductDomain> =
        readProductDrivenPort.findAllByBetweenStartAndFinish(now)
            .filter { now in it.startedAt..it.finishedAt }
}