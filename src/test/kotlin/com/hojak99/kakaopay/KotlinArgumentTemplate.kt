package com.hojak99.kakaopay

import org.mockito.Mockito

class KotlinArgumentMatchers {
    companion object {
        fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
    }
}