package br.com.estudiofalkor.provider.api.configuration

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class CacheInterceptor(private val cacheControl: CacheControl?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var response = chain.proceed(chain.request())
        if (cacheControl != null) {
            response = response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }

        return response
    }
}