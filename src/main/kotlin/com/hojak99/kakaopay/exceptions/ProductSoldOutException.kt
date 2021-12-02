package com.hojak99.kakaopay.exceptions

class ProductSoldOutException(
    override val message: String = "해당 상품은 SOLDOUT 상태입니다.",
    override val code: String = "SOLD_OUT"
) : RuntimeException(), CommonException