package it.coffee.smartcoffee.data

import it.coffee.smartcoffee.domain.CoffeeMachineConnection
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.Coffee
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
class CoffeeMachineConnectionImpl : CoffeeMachineConnection {

    override suspend fun connect(): Result<String> {
        delay(1000)
        return Success("60ba1ab72e35f2d9c786c610")
    }

    override suspend fun brew(coffee: Coffee): Result<Boolean> {
        delay(1000)
        return Success(true)
    }
}