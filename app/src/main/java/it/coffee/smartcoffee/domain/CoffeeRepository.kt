package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.*

interface CoffeeRepository {

    suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo>

    suspend fun getTypes(machine_id: String): Result<List<CoffeeType>>

    suspend fun getSizes(machine_id: String): Result<List<CoffeeSize>>

    suspend fun getExtras(machine_id: String): Result<List<CoffeeExtra>>

}