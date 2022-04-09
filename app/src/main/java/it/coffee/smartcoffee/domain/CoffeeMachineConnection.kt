package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.domain.model.Coffee

interface CoffeeMachineConnection {
    suspend fun connect(): Result<String>
    suspend fun brew(coffee: Coffee): Result<Boolean>
}