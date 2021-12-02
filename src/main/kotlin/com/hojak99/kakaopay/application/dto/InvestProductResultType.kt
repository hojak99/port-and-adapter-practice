package com.hojak99.kakaopay.application.dto

enum class InvestProductResultType(val description: String) {
    SOLD_OUT("매진"), EXCEED("남은 금액 초과"), SUCCESS("성공")
}