package com.hojak99.kakaopay.controller

import com.hojak99.kakaopay.common.CommonResponse
import com.hojak99.kakaopay.exceptions.CommonException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(RuntimeException::class)
    fun common(ex: CommonException): ResponseEntity<CommonResponse<Nothing>> {
        return ResponseEntity(CommonResponse.convertError(ex.code, ex.message), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}