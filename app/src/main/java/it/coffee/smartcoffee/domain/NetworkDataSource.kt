package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo

interface NetworkDataSource {

    suspend fun getMachineInfo(machine_id: String) : Result<CoffeeMachineInfo>

}