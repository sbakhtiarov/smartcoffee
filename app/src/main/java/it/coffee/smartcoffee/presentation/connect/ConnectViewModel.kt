package it.coffee.smartcoffee.presentation.connect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeMachineConnection
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.UnknownError
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

sealed class ConnectionState

class Waiting(val showHelp: Boolean = false) : ConnectionState()
object Connecting : ConnectionState()
class ConnectionSuccess(val machineInfo: CoffeeMachineInfo) : ConnectionState()
class ConnectionFailure(val error: Failure) : ConnectionState()

class ConnectViewModel(
    private val repository: CoffeeRepository,
    private val coffeeMachine: CoffeeMachineConnection,
) : ViewModel() {

    private val _connectionState = MutableLiveData<ConnectionState>(Waiting())
    val connectionState: LiveData<ConnectionState> = _connectionState

    init {
        viewModelScope.launch {
            coffeeMachine.connectionState
                .filterNotNull()
                .collectLatest { state ->
                    when (state) {
                        is CoffeeMachineConnection.Connected -> getMachineInfo(state.machineId)

                        is CoffeeMachineConnection.ConnectionFailure -> {
                            _connectionState.value = ConnectionFailure(UnknownError(state.e))
                        }

                        is CoffeeMachineConnection.Waiting -> scheduleHelpAppear()
                    }
                }
        }
    }

    // Show help message in two seconds if still in Waiting mode
    private fun scheduleHelpAppear() {
        viewModelScope.launch {
            @Suppress("MagicNumber")
            delay(2000)
            if (_connectionState.value is Waiting) {
                _connectionState.value = Waiting(true)
            }
        }
    }

    private suspend fun getMachineInfo(machineId: String) {

        _connectionState.value = Connecting

        when (val result = repository.getMachineInfo(machineId)) {
            is Success -> _connectionState.value = ConnectionSuccess(result.value)
            is Failure -> _connectionState.value = ConnectionFailure(result)
        }
    }

    fun onConnectHandled() {
        _connectionState.value = Waiting()
        scheduleHelpAppear()
    }

    fun onErrorShown() {
        _connectionState.value = Waiting()
        scheduleHelpAppear()
    }

    fun forceConnect() {
        viewModelScope.launch {
            getMachineInfo(coffeeMachine.testMachineId)
        }
    }

}
