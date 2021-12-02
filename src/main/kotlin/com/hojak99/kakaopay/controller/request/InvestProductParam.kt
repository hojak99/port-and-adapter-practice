package com.hojak99.kakaopay.controller.request

import kotlin.random.Random

data class InvestProductParam(
    val amount: Long = Random.nextLong(1, 300)
)