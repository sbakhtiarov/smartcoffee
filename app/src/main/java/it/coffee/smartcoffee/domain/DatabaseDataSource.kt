package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.*

interface DatabaseDataSource {

    suspend fun getMachineInfo(machineId: String): Result<CoffeeMachineInfo>

    suspend fun putMachineInfo(info: CoffeeMachineInfo)

    suspend fun getTypes(machineId: String): Result<List<CoffeeType>>

    suspend fun getSizes(sizeIds: List<String>): Result<List<CoffeeSize>>

    suspend fun getExtras(extraIds: List<String>): Result<List<CoffeeExtra>>

    suspend fun putRecentCoffee(machineId: String, coffee: Coffee)

    suspend fun getRecentCoffee(machineId: String): Result<Coffee>

}