package com.hojak99.kakaopay.controller

import com.hojak99.kakaopay.application.port.driving.GetUserInvestingHistoryDrivingPort
import com.hojak99.kakaopay.common.CommonResponse
import com.hojak99.kakaopay.controller.response.UserInvestingHistoryResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserInvestingHistoryController(
    private val getUserInvestingHistoryDrivingPort: GetUserInvestingHistoryDrivingPort
) {

    @GetMapping("/histories")
    fun getHistory(
        @RequestHeader("X-USER-ID") userId: Int,
    ): CommonResponse<List<UserInvestingHistoryResponse>> =
        CommonResponse.convertData(
            getUserInvestingHistoryDrivingPort.getAll(userId)
                .map { UserInvestingHistoryResponse.from(it) }
        )
}