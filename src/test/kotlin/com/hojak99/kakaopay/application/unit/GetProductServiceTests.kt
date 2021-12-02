package com.hojak99.kakaopay.application.unit

import com.hojak99.kakaopay.KotlinArgumentMatchers
import com.hojak99.kakaopay.KotlinArgumentMatchers.Companion.any
import com.hojak99.kakaopay.application.port.driven.ReadProductDrivenPort
import com.hojak99.kakaopay.application.port.driving.GetProductDrivingPort
import com.hojak99.kakaopay.application.service.GetProductService
import com.hojak99.kakaopay.domain.ProductDomainTestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalDateTime

class GetProductServiceTests {

    private val readProductDrivenPort = mock(ReadProductDrivenPort::class.java)

    private val getProductService: GetProductDrivingPort = GetProductService(
        readProductDrivenPort
    )

    @Test
    fun `상품 조회 - 시작, 끝 시간 필터 적용되는지`() {
        val now = LocalDateTime.now()
        `when`(readProductDrivenPort.findAllByBetweenStartAndFinish(any(LocalDateTime::class.java)))
            .thenReturn(
                listOf(
                    ProductDomainTestUtil.`종료시간 이후`(now),
                    ProductDomainTestUtil.`진행 중인 투자 전 상품`(now)
                )
            )
        val result = getProductService.getAllByBetween(now)
        Assertions.assertFalse(result.any { it.finishedAt < now })
    }

}