package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.*

interface DatabaseDataSource {

    suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo>

    suspend fun putMachineInfo(info: CoffeeMachineInfo)

    suspend fun getTypes(machine_id: String): Result<List<CoffeeType>>

    suspend fun getSizes(sizeIds: List<String>): Result<List<CoffeeSize>>

    suspend fun getExtras(extraIds: List<String>): Result<List<CoffeeExtra>>

    suspend fun putRecentCoffee(machine_id: String, coffee: Coffee)

    suspend fun getRecentCoffee(machine_id: String): Result<Coffee>

}