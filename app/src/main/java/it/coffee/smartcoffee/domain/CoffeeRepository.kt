package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.*

interface CoffeeRepository {

    suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo>

    suspend fun getTypes(machine_id: String): Result<List<CoffeeType>>

    suspend fun getSizes(sizeIds: List<String>): Result<List<CoffeeSize>>

    suspend fun getExtras(extraIds: List<String>): Result<List<CoffeeExtra>>

    suspend fun getRecentCoffee(machine_id: String): Result<Coffee>

    suspend fun putRecentCoffee(machine_id: String, coffee: Coffee)

}