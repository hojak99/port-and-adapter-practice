package com.hojak99.kakaopay

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class KakaopayApplication

fun main(args: Array<String>) {
    runApplication<KakaopayApplication>(*args)
}
