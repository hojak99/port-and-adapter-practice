package com.hojak99.kakaopay.exceptions

class ExceedInvestingAmountException(
    override val message: String = "남은 금액보다 더 많은 금액을 투자하셨습니다. 다시 시도해주세요.",
    override val code: String = "EXCEED_AMOUNT"
) : RuntimeException(), CommonException