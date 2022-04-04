package it.coffee.smartcoffee.data

import it.coffee.smartcoffee.domain.*
import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.*

class CoffeeRepositoryImpl(
    private val database: DatabaseDataSource,
    private val network: NetworkDataSource,
    private val dispatcherIo: CoroutineDispatcher) : CoffeeRepository {

    override suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo> {
        return withContext(dispatcherIo) {

            val (resultNetwork, resultDatabase) = awaitAll(
                async { network.getMachineInfo(machine_id) },
                async { database.getMachineInfo(machine_id) }
            )

            if (resultNetwork is Success) {
                database.putMachineInfo(resultNetwork.value)
                resultNetwork
            } else {
                if (resultDatabase is Success && resultDatabase.value.types.isNotEmpty()) {
                    resultDatabase
                } else {
                    resultNetwork
                }
            }
        }
    }

    override suspend fun getTypes(machine_id: String): Result<List<CoffeeType>> {
        return database.getTypes(machine_id)
    }

    override suspend fun getSizes(sizeIds: List<String>): Result<List<CoffeeSize>> {
        return database.getSizes(sizeIds)
    }

    override suspend fun getExtras(extraIds: List<String>): Result<List<CoffeeExtra>> {
        return database.getExtras(extraIds)
    }
}