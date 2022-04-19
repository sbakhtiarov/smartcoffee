package it.coffee.smartcoffee.data

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import it.coffee.smartcoffee.domain.CoffeeMachineConnection
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.Coffee
import it.coffee.smartcoffee.domain.model.CoffeeType
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest

class CoffeeMachineConnectionImplTest : TestCase() {

    fun `test Initial State`() {
        val machine = CoffeeMachineConnectionImpl()
        assertEquals(machine.connectionState.value, CoffeeMachineConnection.Waiting)
    }

    fun `test connection state after reading tag`() {

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        runTest {
            val machine = CoffeeMachineConnectionImpl()
            machine.onNfcTagRead(NfcTag(null))

            assert(machine.connectionState.value is CoffeeMachineConnection.Connected)
        }
    }

    fun `test machine id after reading tag`() {

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        runTest {
            val machine = CoffeeMachineConnectionImpl()
            machine.onNfcTagRead(NfcTag(null))

            assertEquals((machine.connectionState.value as CoffeeMachineConnection.Connected).machineId,
                machine.testMachineId)
        }
    }

    fun `test connection state after reset`() {
        runTest {
            val machine = CoffeeMachineConnectionImpl()
            machine.onNfcTagRead(NfcTag(null))
            machine.resetConnection()
            assertEquals(machine.connectionState.value, CoffeeMachineConnection.Waiting)
        }
    }

    fun `test brew result`() {
        runTest {
            val machine = CoffeeMachineConnectionImpl()
            machine.onNfcTagRead(NfcTag(null))
            val result = machine.brew(Coffee(CoffeeType("1","", null, null), null, emptyList()))
            assert(result is Success) { "Expected Success result, but got $result." }
        }
    }

}