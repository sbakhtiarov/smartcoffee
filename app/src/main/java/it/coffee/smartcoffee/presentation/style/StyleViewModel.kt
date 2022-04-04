package it.coffee.smartcoffee.presentation.style

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.*
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.launch

class StyleViewModel(private val machine_id: String, private val repository: CoffeeRepository) : ViewModel() {

    private val _styles = MutableLiveData<List<CoffeeType>>()
    val styles : LiveData<List<CoffeeType>> = _styles

    init {
        viewModelScope.launch {

            val result = repository.getTypes(machine_id)

            when (result) {
                is Success -> _styles.value = result.value
                is Failure -> error(result.exception)
            }

        }
    }
}