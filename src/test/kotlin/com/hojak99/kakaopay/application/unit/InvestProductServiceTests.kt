package com.hojak99.kakaopay.application.unit

import com.hojak99.kakaopay.KotlinArgumentMatchers.Companion.any
import com.hojak99.kakaopay.application.dto.InvestProductResultType
import com.hojak99.kakaopay.application.port.driven.ReadProductDrivenPort
import com.hojak99.kakaopay.application.port.driven.UpdateInvestDrivenPort
import com.hojak99.kakaopay.application.port.driven.WriteUserInvestingHistoryDrivenPort
import com.hojak99.kakaopay.application.port.driving.InvestProductDrivingPort
import com.hojak99.kakaopay.application.service.InvestProductService
import com.hojak99.kakaopay.domain.ProductDomainTestUtil
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import com.hojak99.kakaopay.exceptions.ExceedInvestingAmountException
import com.hojak99.kakaopay.exceptions.NotAvailableProductException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*

class InvestProductServiceTests {
    private val readProductDrivenPort = mock(ReadProductDrivenPort::class.java)
    private val updateInvestDrivenPort = mock(UpdateInvestDrivenPort::class.java)
    private val writeUserInvestingHistoryDrivenPort = mock(WriteUserInvestingHistoryDrivenPort::class.java)

    private val investProductService: InvestProductDrivingPort = InvestProductService(
        readProductDrivenPort, updateInvestDrivenPort, writeUserInvestingHistoryDrivenPort
    )

    @Test
    fun `사용자 투자 요청 시 - 대기 중인 상품일 시 - 비정상`() {
        val now = LocalDateTime.now()
        val userId = 123144
        val product = ProductDomainTestUtil.`대기 상태`(now)
        val requestAmount = product.remainInvestingAmount - 1

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        assertThrows<NotAvailableProductException> {
            investProductService.request(userId, product.id, requestAmount)
        }
    }

    @Test
    fun `사용자 투자 요청 시 - 요청금액 보다 남은 금액이 더 많을 때 - 정상`() {
        val now = LocalDateTime.now()
        val userId = 123144
        val product = ProductDomainTestUtil.`진행 중인 투자 전 상품`(now)
        val requestAmount = product.remainInvestingAmount - 1

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        `when`(updateInvestDrivenPort.tryDecrementRemainAmount(anyInt(), anyInt(), anyLong()))
            .thenReturn(InvestProductResultType.SUCCESS)

        `when`(writeUserInvestingHistoryDrivenPort.insert(any(UserInvestingHistoryDomain::class.java)))
            .thenAnswer {
                val data = returnsFirstArg<UserInvestingHistoryDomain>().answer(it)
                assertEquals(product.id, data.productId)
                assertEquals(requestAmount, data.investingAmount)
                assertEquals(userId, data.userId)
                data
            }

        val result = investProductService.request(userId, product.id, requestAmount)
        assertEquals(InvestProductResultType.SUCCESS, result)
    }

    @Test
    fun `사용자 투자 요청 시 - 요청금액이 남은 금액보다 클 때 - 비정상`() {
        val userId = 1313131
        val requestAmount = 100000000000L
        val now = LocalDateTime.now()
        val product = ProductDomainTestUtil.`진행 중인 상품`(100000000009L, 1L, now)

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        assertThrows<ExceedInvestingAmountException> {
            investProductService.request(userId, product.id, requestAmount)
        }
    }

    @Test
    fun `사용자 투자 요청 시 - 요청금액과 남은 금액 같을 때 - 정상`() {
        val userId = 1313131
        val requestAmount = 1L
        val now = LocalDateTime.now()
        val product = ProductDomainTestUtil.`진행 중인 상품`(100000000009L, 1L, now)

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        `when`(updateInvestDrivenPort.tryDecrementRemainAmount(anyInt(), anyInt(), anyLong()))
            .thenReturn(InvestProductResultType.SUCCESS)

        `when`(writeUserInvestingHistoryDrivenPort.insert(any(UserInvestingHistoryDomain::class.java)))
            .thenAnswer {
                val data = returnsFirstArg<UserInvestingHistoryDomain>().answer(it)
                assertEquals(product.id, data.productId)
                assertEquals(requestAmount, data.investingAmount)
                assertEquals(userId, data.userId)
                data
            }

        val result = investProductService.request(userId, product.id, requestAmount)
        assertEquals(InvestProductResultType.SUCCESS, result)
    }

    @Test
    fun `사용자 투자 요청 시 - 이미 모집 마감 일 때 - 비정상`() {
        val userId = 1313131
        val requestAmount = 100000000000L
        val now = LocalDateTime.now()
        val product = ProductDomainTestUtil.`금액 마감`(now)

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        assertThrows<NotAvailableProductException> {
            investProductService.request(userId, product.id, requestAmount)
        }
    }

    @Test
    fun `사용자 투자 요청 시 - validation 통과 후 decrement 시에서 SOLD OUT 될 때 - 비정상`() {
        val userId = 1313131
        val requestAmount = 1L
        val now = LocalDateTime.now()
        val product = ProductDomainTestUtil.`진행 중인 상품`(100000000009L, 1L, now)

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        `when`(updateInvestDrivenPort.tryDecrementRemainAmount(anyInt(), anyInt(), anyLong()))
            .thenReturn(InvestProductResultType.SOLD_OUT)

        val result = investProductService.request(userId, product.id, requestAmount)
        assertEquals(InvestProductResultType.SOLD_OUT, result)
    }

    @Test
    fun `사용자 투자 요청 시 - validation 통과 후 decrement 시에서 남은금액 초과 시 - 비정상`() {
        val userId = 1313131
        val requestAmount = 1L
        val now = LocalDateTime.now()
        val product = ProductDomainTestUtil.`진행 중인 상품`(100000000009L, 1L, now)

        `when`(readProductDrivenPort.findById(anyInt()))
            .thenReturn(product)

        `when`(updateInvestDrivenPort.tryDecrementRemainAmount(anyInt(), anyInt(), anyLong()))
            .thenReturn(InvestProductResultType.EXCEED)

        assertThrows<ExceedInvestingAmountException> {
            investProductService.request(userId, product.id, requestAmount)
        }
    }
}