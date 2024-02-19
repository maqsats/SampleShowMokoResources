package com.dnapayments.mp.domain.model.map

import com.dnapayments.mp.domain.network.Response


abstract class Mapper<FROM, TO> {

    abstract fun mapData(from: FROM): TO

    fun map(from: Response<FROM>): Response<TO> = mapSimpleResult(from)

    private fun mapSimpleResult(from: Response<FROM>): Response<TO> = when (from) {
        is Response.Error -> Response.Error(from.error)
        is Response.NetworkError -> Response.NetworkError
        is Response.Success -> Response.Success(mapData(from.data))
        is Response.TokenExpire -> Response.TokenExpire
    }
}
