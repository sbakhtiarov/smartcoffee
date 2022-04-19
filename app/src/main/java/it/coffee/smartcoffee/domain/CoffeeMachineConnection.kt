package it.coffee.smartcoffee.domain

import it.coffee.smartcoffee.data.NfcTag
import it.coffee.smartcoffee.domain.model.Coffee
import kotlinx.coroutines.flow.Flow

interface CoffeeMachineConnection {

    val testMachineId: String
    val connectionState: Flow<CoffeeMachineConnectionState>

    suspend fun brew(coffee: Coffee): Result<Boolean>
    suspend fun resetConnection()
    suspend fun onNfcTagRead(tag: NfcTag)

    sealed class CoffeeMachineConnectionState
    object Waiting : CoffeeMachineConnectionState()
    class Connected(val machineId: String) : CoffeeMachineConnectionState()
    class ConnectionFailure(val e: Exception) : CoffeeMachineConnectionState()

}
