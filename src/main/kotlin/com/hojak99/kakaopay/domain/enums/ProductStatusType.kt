package com.hojak99.kakaopay.domain.enums

enum class ProductStatusType(val description: String) {
    WAIT("작성 중"),
    ING("투자 진행 중"),
    DONE("투자 종료");


    fun isAvailable() = this == ING
}