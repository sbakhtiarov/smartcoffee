package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType

interface DatabaseDataSource {

    suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo>

    suspend fun putMachineInfo(info: CoffeeMachineInfo)

    suspend fun getTypes(machine_id: String): Result<List<CoffeeType>>

    suspend fun getSizes(machine_id: String): Result<List<CoffeeSize>>

    suspend fun getExtras(machine_id: String): Result<List<CoffeeExtra>>

}