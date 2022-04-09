package it.coffee.smartcoffee.data.network

import it.coffee.smartcoffee.BuildConfig
import it.coffee.smartcoffee.domain.NetworkDataSource
import it.coffee.smartcoffee.domain.NetworkError
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class NetworkDataSourceImpl : NetworkDataSource {

    private val coffeeApi: CoffeeApi

    init {

        val client = with(OkHttpClient.Builder()) {

            if (BuildConfig.DEBUG) {
                val logInterceptor = HttpLoggingInterceptor()
                logInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logInterceptor)
            }

            build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://darkroastedbeans.coffeeit.nl/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        coffeeApi = retrofit.create(CoffeeApi::class.java)
    }

    override suspend fun getMachineInfo(machineId: String): Result<CoffeeMachineInfo> {
        return try {
            Success(coffeeApi.getMachineInfo(machineId))
        } catch (e: IOException) {
            NetworkError(e)
        }
    }
}

interface CoffeeApi {

    @GET("/coffee-machine/{machine_id}")
    suspend fun getMachineInfo(@Path("machine_id") machineId: String) : CoffeeMachineInfo

}