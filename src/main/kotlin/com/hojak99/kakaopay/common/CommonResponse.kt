package com.hojak99.kakaopay.common


class CommonResponse<T>(
    val data: T?,
    val error: String?,
    val message: String?
) {
    companion object {
        fun error() =
            CommonResponse<String>(
                data = null,
                error = "INTERNAL_SERVER_ERROR",
                message = "알 수 없는 오류가 발생했습니다."
            )

        fun <T> convertData(data: T) =
            CommonResponse(
                data = data,
                error = null,
                message = null
            )

        fun <T> convertData(data: T, message: String) =
            CommonResponse(
                data = data,
                error = null,
                message = message
            )

        fun convertError(code: String, message: String) =
            CommonResponse(
                data = null,
                error = code,
                message = message
            )

        fun empty() =
            CommonResponse(
                data = null,
                error = null,
                message = null
            )
    }
}