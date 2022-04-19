package it.coffee.smartcoffee.data.network

import it.coffee.smartcoffee.domain.NetworkClient
import it.coffee.smartcoffee.domain.NetworkDataSource
import it.coffee.smartcoffee.domain.NetworkError
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.create
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class NetworkDataSourceImpl(networkClient: NetworkClient) : NetworkDataSource {

    private val coffeeApi: CoffeeApi = networkClient.create()

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