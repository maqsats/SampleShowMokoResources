package com.dna.payments.kmm.domain.network

import com.dna.payments.kmm.utils.UiText


sealed class Response<out T> {

    data class Success<out T>(val data: T) : Response<T>()

    data class Error(val error: UiText) : Response<Nothing>()

    data object TokenExpire : Response<Nothing>()

    data object NetworkError : Response<Nothing>()
}