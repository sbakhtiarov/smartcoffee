package it.coffee.smartcoffee.presentation.size

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.launch

class SizeViewModel(private val style: CoffeeType, private val repository: CoffeeRepository) : ViewModel() {

    private val _sizes = MutableLiveData<List<CoffeeSize>>()
    val sizes : LiveData<List<CoffeeSize>> = _sizes

    init {
        viewModelScope.launch {
            val result = repository.getSizes(style.sizes ?: error("No sizes defined"))

            when (result) {
                is Success -> _sizes.value = result.value
                is Failure -> error(result.exception)
            }
        }
    }
}