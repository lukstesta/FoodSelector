package br.com.estudiofalkor.provider.api.configuration

import android.content.Context
import br.com.estudiofalkor.provider.R

class ApiEndPoint(context: Context) {

    val urlApi = context.getString(R.string.url_api)
    val apiKey = context.getString(R.string.api_client_key)
}