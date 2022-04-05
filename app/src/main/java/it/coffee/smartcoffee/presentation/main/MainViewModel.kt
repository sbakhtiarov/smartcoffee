package it.coffee.smartcoffee.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.model.*
import kotlinx.coroutines.launch

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
        _navigate.value = R.id.action_navigation_connect_to_style
    }

    fun setStyle(style: CoffeeType) {
        coffee = Coffee(style = style, size = null, extra = emptyList())

        if (!style.sizes.isNullOrEmpty()) {
            _navigate.value = R.id.action_navigate_style_to_size
        } else {
            if (!style.extras.isNullOrEmpty()) {
                _navigate.value = R.id.action_navigate_style_to_extras
            } else {
                _navigate.value = R.id.action_navigate_style_to_overview
            }
        }

    }

    fun setSize(size: CoffeeSize) {
        coffee = coffee?.copy(size = size)
        if (!coffee?.style?.extras.isNullOrEmpty()) {
            _navigate.value = R.id.action_navigate_size_to_extras
        } else {
            _navigate.value = R.id.action_navigate_size_to_overview
        }
    }

    fun setExtras(extras: List<CoffeeExtra>) {
        coffee = coffee?.copy(extra = extras)
        _navigate.value = R.id.action_navigate_extras_to_overview
    }

    fun confirmCoffee() {
        _navigate.value = R.id.action_navigation_overview_to_enjoy

        viewModelScope.launch {
            repository.putRecentCoffee(checkNotNull(machineId), checkNotNull(coffee))
        }
    }

    fun restartCoffeeBuilder() {
        coffee = null
        _navigate.value = R.id.action_navigation_enjoy_to_style
    }

    fun repeatCoffee(coffee: Coffee) {
        this.coffee = coffee
        _navigate.value = R.id.action_navigate_style_to_overview
    }
}