package it.coffee.smartcoffee.presentation.style

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.launch

class StyleViewModel(private val machine_id: String, private val repository: CoffeeRepository) : ViewModel() {

    private val _items = MutableLiveData<List<StyleListItem>>()
    val items : LiveData<List<StyleListItem>> = _items

    private var styles: List<CoffeeType>? = null

    init {
        viewModelScope.launch {
            when (val result = repository.getTypes(machine_id)) {
                is Success -> {
                    styles = result.value
                    _items.value = styles?.map {
                        StyleListItem(it.id, getIcon(it.name), it.name)
                    }
                }
                is Failure -> error(result.exception)
            }
        }
    }

    fun getCoffeeType(styleId: String): CoffeeType {
        return styles?.find { it.id == styleId } ?: error("Style not found")
    }

    // TODO: Bad idea to get icon based on the coffee name but I am not sure if type ids are stable
    private fun getIcon(name: String): Int {
        return when (name.lowercase()) {
            "cappuccino" -> R.drawable.ic_cappuccino
            "espresso" -> R.drawable.ic_espresso
            else -> R.drawable.ic_coffee_medium
        }
    }
}

data class StyleListItem(val id: String, @DrawableRes val icon: Int, val name: String)
