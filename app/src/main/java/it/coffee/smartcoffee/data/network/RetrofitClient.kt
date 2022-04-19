package it.coffee.smartcoffee.data.network

import it.coffee.smartcoffee.BuildConfig
import it.coffee.smartcoffee.domain.NetworkClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(serviceUrl: String) : NetworkClient {

    private val retrofit: Retrofit

    init {

        val client = with(OkHttpClient.Builder()) {

            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logInterceptor)
            }

            build()
        }

        retrofit = Retrofit.Builder()
            .baseUrl(serviceUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun <T> createApi(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}