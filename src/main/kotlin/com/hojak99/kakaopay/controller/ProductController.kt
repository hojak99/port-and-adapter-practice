package com.hojak99.kakaopay.controller

import com.hojak99.kakaopay.application.dto.InvestProductResultType
import com.hojak99.kakaopay.application.port.driving.GetProductDrivingPort
import com.hojak99.kakaopay.application.port.driving.InvestProductDrivingPort
import com.hojak99.kakaopay.common.CommonResponse
import com.hojak99.kakaopay.controller.request.InvestProductParam
import com.hojak99.kakaopay.controller.response.ProductResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/v1/products")
class ProductController(
    private val getProductDrivingPort: GetProductDrivingPort,
    private val investProductDrivingPort: InvestProductDrivingPort
) {

    @GetMapping("")
    fun getProduct(): CommonResponse<List<ProductResponse>> {
        val now = LocalDateTime.now()
        return CommonResponse.convertData(
            getProductDrivingPort.getAllByBetween(now).map { ProductResponse.from(it) }
        )
    }

    @PostMapping("/{id}")
    fun invest(
        @PathVariable("id") productId: Int,
        @RequestHeader("X-USER-ID") userId: Int,
        @RequestBody request: InvestProductParam
    ): CommonResponse<InvestProductResultType> {
        val type = investProductDrivingPort.request(userId, productId, request.amount)
        return CommonResponse.convertData(type)
    }

}