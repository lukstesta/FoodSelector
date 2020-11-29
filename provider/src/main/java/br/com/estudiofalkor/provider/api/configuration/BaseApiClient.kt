package br.com.estudiofalkor.provider.api.configuration

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

abstract class BaseApiClient(
    private val context: Context,
    private val headerInterceptor: Interceptor,
    private val cacheControl: CacheControl? = null
) {

    private val timeout = 45L

    private val cacheSize = 10_485_760 // 10 MB

    private fun createOkHttpClient(timeout: Long): OkHttpClient = try {
        val httpCacheDirectory = File(context.cacheDir, "okHttpClient")
        val localCache = Cache(httpCacheDirectory, cacheSize.toLong())
        OkHttpClient.Builder().apply {
            addInterceptor(headerInterceptor)
            addNetworkInterceptor(CacheInterceptor(cacheControl))
            connectTimeout(timeout, TimeUnit.SECONDS)
            readTimeout(timeout, TimeUnit.SECONDS)
            writeTimeout(timeout, TimeUnit.SECONDS)
            cache(localCache)
        }.build()
    } catch (e: Exception) {
        Log.e(ApiClient::class.java.simpleName, e.message.toString())
        throw e
    }

    fun client(): OkHttpClient {
        return createOkHttpClient(timeout)
    }
}