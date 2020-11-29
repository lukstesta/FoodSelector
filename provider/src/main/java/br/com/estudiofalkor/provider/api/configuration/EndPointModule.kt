package br.com.estudiofalkor.provider.api.configuration

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyStore
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.TrustManagerFactory

const val API = "api"
private const val API_HEADER = "apiHeader"
private const val API_CLIENT = "apiClient"

private const val HEADER_CONTENT_TYPE = "Content-Type"
private const val CONTENT_TYPE_JSON = "application/json"

private const val API_KEY = "apiKey"

val endPointModule = module {

    single { ApiEndPoint(androidContext()) }

    single {
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(null as KeyStore?)
        }
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()
    }

    single(named(API_HEADER)) {
        Interceptor { chain ->
            val original = chain.request()

            val builder = chain.setupHeaderParameters()

            val urlWithKey = original.url().newBuilder()
                .addQueryParameter(API_KEY, get<ApiEndPoint>().apiKey).build()

            builder.method(original.method(), original.body())
            builder.url(urlWithKey)

            chain.proceed(builder.build())
        }
    }

    val cacheControl = CacheControl.Builder()
        .maxAge(1, TimeUnit.HOURS)
        .build()

    single(named(API_CLIENT)) { ApiClient(get(), get(named(API_HEADER)), cacheControl) }

    single(named(API)) {
        Retrofit.Builder()
            .baseUrl(get<ApiEndPoint>().urlApi)
            .client(get<ApiClient>(named(API_CLIENT)).client())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .addCallAdapterFactory(EndpointCallAdapterFactory(EndPoint.API))
            .build()
    }
}

private fun Interceptor.Chain.setupHeaderParameters(): Request.Builder {
    val original = this.request()
    val builder = original.newBuilder()

    builder.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON)

    return builder
}