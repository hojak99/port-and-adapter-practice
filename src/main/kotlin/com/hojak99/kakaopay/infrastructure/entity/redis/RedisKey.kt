package com.hojak99.kakaopay.infrastructure.entity.redis

object RedisKey {
    fun getHistoryKey(productId: Int, userId: Int, requestId: String) =
        "product:$productId:userId:$userId:requestId:$requestId"

    fun getProductRemainAmountKey(productId: Int) =
        "product:$productId:remain"

    fun getProductInvestorCount(productId: Int) =
        "product:$productId:investor_count"

    fun getActiveProductKey() = "product"
}