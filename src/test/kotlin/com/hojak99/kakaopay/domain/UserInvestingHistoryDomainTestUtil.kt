package com.hojak99.kakaopay.domain

import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

object UserInvestingHistoryDomainTestUtil {
    fun `오름차순 생성날짜`(userId: Int) =
        listOf(
            UserInvestingHistoryDomain(
                id = Random.nextInt(),
                userId = userId,
                productId = Random.nextInt(),
                investingAmount = Random.nextInt(1, 1000).toLong(),
                createdAt = LocalDateTime.now().minusDays(5),
                totalInvestingAmount = Random.nextLong(5000, 10000),
                title = "asd"
            ),
            UserInvestingHistoryDomain(
                id = Random.nextInt(),
                userId = userId,
                productId = Random.nextInt(),
                investingAmount = Random.nextInt(1, 1000).toLong(),
                createdAt = LocalDateTime.now().minusDays(1),
                totalInvestingAmount = Random.nextLong(5000, 10000),
                title = "asd"
            )
        )

}