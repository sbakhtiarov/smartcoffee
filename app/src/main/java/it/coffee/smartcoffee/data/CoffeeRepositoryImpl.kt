package it.coffee.smartcoffee.data

import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.DatabaseDataSource
import it.coffee.smartcoffee.domain.NetworkDataSource
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.Coffee
import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class CoffeeRepositoryImpl(
    private val database: DatabaseDataSource,
    private val network: NetworkDataSource,
    private val dispatcherIo: CoroutineDispatcher) : CoffeeRepository {

    override suspend fun getMachineInfo(machineId: String): Result<CoffeeMachineInfo> {
        return withContext(dispatcherIo) {

            val (resultNetwork, resultDatabase) = awaitAll(
                async { network.getMachineInfo(machineId) },
                async { database.getMachineInfo(machineId) }
            )

            // Always check network result first in case machine capabilities changed
            // Use database cache if network is not available

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

    override suspend fun getTypes(machineId: String): Result<List<CoffeeType>> {
        return database.getTypes(machineId)
    }

    override suspend fun getSizes(sizeIds: List<String>): Result<List<CoffeeSize>> {
        return database.getSizes(sizeIds)
    }

    override suspend fun getExtras(extraIds: List<String>): Result<List<CoffeeExtra>> {
        return database.getExtras(extraIds)
    }

    override suspend fun putRecentCoffee(machineId: String, coffee: Coffee) {
        withContext(dispatcherIo + NonCancellable) {
            database.putRecentCoffee(machineId, coffee)
        }
    }

    override suspend fun getRecentCoffee(machineId: String): Result<Coffee> {
        return database.getRecentCoffee(machineId)
    }
}
