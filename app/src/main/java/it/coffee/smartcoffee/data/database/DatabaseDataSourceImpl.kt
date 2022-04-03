package it.coffee.smartcoffee.data.database

import it.coffee.smartcoffee.domain.DatabaseDataSource
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.NetworkError
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.UnknownError
import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import java.lang.IllegalStateException

class DatabaseDataSourceImpl : DatabaseDataSource {

    override suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo> {
        return UnknownError(IllegalStateException("stub"))
    }

    override suspend fun putMachineInfo(info: CoffeeMachineInfo) {

    }

    override suspend fun getTypes(machine_id: String): Result<List<CoffeeType>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSizes(machine_id: String): Result<List<CoffeeSize>> {
        TODO("Not yet implemented")
    }

    override suspend fun getExtras(machine_id: String): Result<List<CoffeeExtra>> {
        TODO("Not yet implemented")
    }
}