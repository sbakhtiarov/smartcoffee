package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo

interface NetworkDataSource {

    suspend fun getMachineInfo(machineId: String) : Result<CoffeeMachineInfo>

}