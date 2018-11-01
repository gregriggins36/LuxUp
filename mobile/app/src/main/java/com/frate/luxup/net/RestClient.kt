package com.frate.luxup.net

import com.frate.luxup.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RestClient(private val restClientConfig: RestClientConfig) {
    fun createRetrofitAdapter(hostUrl: String): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(restClientConfig.callAdapterFactory)
        .addConverterFactory(restClientConfig.converterFactory)
        .client(okHttpClient())
        .baseUrl(hostUrl)
        .build()

    private fun okHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(restClientConfig.readTimeOutValue, TimeUnit.SECONDS)
            .writeTimeout(restClientConfig.writeTimeOutValue, TimeUnit.SECONDS)
            .connectTimeout(restClientConfig.connectTimeOutValue, TimeUnit.SECONDS)

        restClientConfig.interceptors().forEach { it -> builder.addInterceptor(it) }

        if (BuildConfig.DEBUG) builder.hostnameVerifier { _, _ -> true }

        return builder.build()
    }
}