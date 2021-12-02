package com.hojak99.kakaopay.common

interface EntityDomainType<T> {
    fun convertDomain(): T
}