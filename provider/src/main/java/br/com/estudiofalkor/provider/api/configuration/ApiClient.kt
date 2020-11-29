package br.com.estudiofalkor.provider.api.configuration

import android.content.Context
import okhttp3.CacheControl
import okhttp3.Interceptor

class ApiClient(
    context: Context,
    headerInterceptor: Interceptor,
    cacheControl: CacheControl? = null
) : BaseApiClient(
    context,
    headerInterceptor,
    cacheControl
)