package it.coffee.smartcoffee.presentation.extra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.launch

class ExtraViewModel(private val style: CoffeeType, private val repository: CoffeeRepository) : ViewModel() {

    private val _extras = MutableLiveData<List<CoffeeExtra>>()
    val extras : LiveData<List<CoffeeExtra>> = _extras

    init {
        viewModelScope.launch {
            val result = repository.getExtras(style.extras ?: error("No extras defined"))

            when (result) {
                is Success -> _extras.value = result.value
                is Failure -> error(result.exception)
            }
        }
    }
}