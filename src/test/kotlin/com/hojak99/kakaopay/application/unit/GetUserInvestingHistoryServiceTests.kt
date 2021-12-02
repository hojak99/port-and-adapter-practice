package com.hojak99.kakaopay.application.unit

import com.hojak99.kakaopay.application.port.driven.ReadUserInvestingHistoryDrivenPort
import com.hojak99.kakaopay.application.port.driving.GetUserInvestingHistoryDrivingPort
import com.hojak99.kakaopay.application.service.GetUserInvestingHistoryService
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomain
import com.hojak99.kakaopay.domain.UserInvestingHistoryDomainTestUtil
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class GetUserInvestingHistoryServiceTests {

    private val readUserInvestingHistoryDrivenPort = mock(ReadUserInvestingHistoryDrivenPort::class.java)

    private val getUserInvestingHistoryService: GetUserInvestingHistoryDrivingPort =
        GetUserInvestingHistoryService(
            readUserInvestingHistoryDrivenPort
        )

    @Test
    fun `투자 요청 시간 내림차순 정렬되는가`() {
        val userId = 1311355
        val data = UserInvestingHistoryDomainTestUtil.`오름차순 생성날짜`(userId)

        `when`(readUserInvestingHistoryDrivenPort.findAllByUserId(anyInt()))
            .thenReturn(data)

        val result = getUserInvestingHistoryService.getAll(userId)

        var first: UserInvestingHistoryDomain? = null

        Assertions.assertTrue(result.size > 1)
        result.forEachIndexed { index, userInvestingHistoryDomain ->
            if (index == 0) first = userInvestingHistoryDomain
            else {
                Assertions.assertFalse(first!!.createdAt < userInvestingHistoryDomain.createdAt)
            }
        }
    }

}