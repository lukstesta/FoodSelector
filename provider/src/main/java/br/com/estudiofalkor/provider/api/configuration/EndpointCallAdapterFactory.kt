package br.com.estudiofalkor.provider.api.configuration

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EndpointCallAdapterFactory(private val endPoints: EndPoint) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }

        val responseType = getCallResponseType(returnType)

        return object : CallAdapter<Any, Call<*>> {
            override fun responseType() = responseType

            override fun adapt(call: Call<Any>) = EndPointCall(call, endPoints)
        }
    }

    private fun getCallResponseType(returnType: Type) =
        if (returnType !is ParameterizedType) {
            throw IllegalArgumentException(
                "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>"
            )
        } else {
            getParameterUpperBound(0, returnType)
        }
}