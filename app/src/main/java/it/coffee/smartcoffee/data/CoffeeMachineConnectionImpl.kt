package it.coffee.smartcoffee.data

import android.nfc.Tag
import android.util.Log
import it.coffee.smartcoffee.domain.CoffeeMachineConnection
import it.coffee.smartcoffee.domain.CoffeeMachineConnection.CoffeeMachineConnectionState
import it.coffee.smartcoffee.domain.CoffeeMachineConnection.Connected
import it.coffee.smartcoffee.domain.CoffeeMachineConnection.Waiting
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.Coffee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

@JvmInline
value class NfcTag(val tag: Tag?)

@Suppress("MagicNumber")
class CoffeeMachineConnectionImpl : CoffeeMachineConnection {

    override val testMachineId: String = "60ba1ab72e35f2d9c786c610"
    override val connectionState = MutableStateFlow<CoffeeMachineConnectionState>(Waiting)

    override suspend fun onNfcTagRead(tag: NfcTag) {
        withContext(Dispatchers.IO) {
            Log.e("NFC", "NFC Tag: $tag")
            connectionState.emit(Connected(testMachineId))
        }
    }

    override suspend fun brew(coffee: Coffee): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            delay(1000)
            Success(true)
        }
    }

    override suspend fun resetConnection() {
        connectionState.emit(Waiting)
    }
}
