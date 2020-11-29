package br.com.estudiofalkor.provider.api.configuration

import retrofit2.Call

class EndPointCall<T>(call: Call<T>, val endPoint: EndPoint): Call<T> by call