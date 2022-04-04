package it.coffee.smartcoffee.presentation.connect

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeMachineInfo
import kotlinx.coroutines.launch

sealed class ConnectionState

object Waiting : ConnectionState()
object Connecting : ConnectionState()
class ConnectionSuccess(val machineInfo: CoffeeMachineInfo) : ConnectionState()
class ConnectionFailure(val error: Failure) : ConnectionState()

class ConnectViewModel(private val repository: CoffeeRepository) : ViewModel() {

    private val _connectionState = MutableLiveData<ConnectionState>(Waiting)
    val connectionState: LiveData<ConnectionState> = _connectionState

    fun getMachineInfo(machine_id: String) {

        _connectionState.value = Connecting

        viewModelScope.launch {
            when (val result = repository.getMachineInfo(machine_id)) {
                is Success -> _connectionState.value = ConnectionSuccess(result.value)
                is Failure -> _connectionState.value = ConnectionFailure(result)
            }
        }
    }

    fun onConnectHandled() {
        _connectionState.value = Waiting
    }

    fun onErrorShown() {
        _connectionState.value = Waiting
    }

}