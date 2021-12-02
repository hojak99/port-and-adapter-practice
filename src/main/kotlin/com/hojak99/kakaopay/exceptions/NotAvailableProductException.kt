package com.hojak99.kakaopay.exceptions

class NotAvailableProductException(
    override val message: String = "해당 투자 상품은 이용하실 수 없습니다.",
    override val code: String = "NOT_AVAILABLE_PRODUCT"
) : RuntimeException(), CommonException