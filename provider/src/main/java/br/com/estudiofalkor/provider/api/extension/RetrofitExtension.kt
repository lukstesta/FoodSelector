package br.com.estudiofalkor.provider.api.extension

import br.com.estudiofalkor.infrastructure.errorHandling.*
import br.com.estudiofalkor.infrastructure.network.StatusCode
import br.com.estudiofalkor.provider.api.configuration.DefaultErrorConverter
import okhttp3.Headers
import retrofit2.Call
import retrofit2.Response

fun <T> Call<T>.fetch(errorConverter: ErrorConverter = DefaultErrorConverter()): T {
    val (data, _) = fetchWithHeader(errorConverter)
    return data
}

fun <T> Call<T>.fetchWithHeader(errorConverter: ErrorConverter = DefaultErrorConverter()): Pair<T, Headers> {
    try {
        val response = execute()

        checkSuccess<T>(response)

        val body = response.body()

        if (body == null) {
            throw ErrorResponse(Unexpected, cause = NullBodyException())
        } else {
            return Pair(body, response.headers())
        }
    } catch (e: Exception) {
        throw errorConverter.convert(e)!!
    }
}

private fun <T> checkSuccess(response: Response<T>) {
    if (!response.isSuccessful) {
        throw ErrorResponse(
            Http(
                StatusCode.byCode(response.code()),
                response.errorBody()?.string()
            ),
            response.message()
        )
    }
}