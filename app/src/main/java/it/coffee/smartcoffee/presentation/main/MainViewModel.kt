package it.coffee.smartcoffee.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.*

class MainViewModel(private val repository: CoffeeRepository): ViewModel() {

    var machineInfo: CoffeeMachineInfo? = null
    var coffee: Coffee? = null

    val machineId: String?
        get() = machineInfo?.id

    private val _navigate = MutableLiveData<Int?>()
    val navigate: LiveData<Int?> = _navigate

    fun onNavigateComplete() {
        _navigate.value = null
    }

    fun onConnected(machineInfo: CoffeeMachineInfo) {
        this.machineInfo = machineInfo
        _navigate.value = R.id.navigation_style
    }

    fun setStyle(style: CoffeeType) {
        coffee = Coffee(style = style, size = null, extra = emptyList())

        if (!style.sizes.isNullOrEmpty()) {
            _navigate.value = R.id.navigation_size
        } else {
            if (!style.extras.isNullOrEmpty()) {
                _navigate.value = R.id.navigation_extras
            } else {
                _navigate.value = R.id.navigation_brew
            }
        }

    }

    fun setSize(size: CoffeeSize) {
        coffee = coffee?.copy(size = size)
        if (!coffee?.style?.extras.isNullOrEmpty()) {
            _navigate.value = R.id.navigation_extras
        } else {
            _navigate.value = R.id.navigation_brew
        }
    }

    fun setExtras(extras: List<CoffeeExtra>) {
        coffee = coffee?.copy(extra = extras)
        _navigate.value = R.id.navigation_brew
    }

    fun restartCoffeeBuilder() {
        coffee = null
        _navigate.value = R.id.action_navigation_to_style
    }
}