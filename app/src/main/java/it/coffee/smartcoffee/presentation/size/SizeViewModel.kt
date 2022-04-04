package it.coffee.smartcoffee.presentation.size

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import kotlinx.coroutines.launch

class SizeViewModel(private val style: CoffeeType, private val repository: CoffeeRepository) : ViewModel() {

    private val _items = MutableLiveData<List<SizeListItem>>()
    val items : LiveData<List<SizeListItem>> = _items

    private var sizes: List<CoffeeSize>? = null

    init {
        viewModelScope.launch {
            val result = repository.getSizes(style.sizes ?: error("No sizes defined"))

            when (result) {
                is Success -> {
                    sizes = result.value
                    _items.value = sizes?.map {
                        SizeListItem(it.id, getIcon(it.name), it.name)
                    }
                }
                is Failure -> error(result.exception)
            }
        }
    }

    private fun getIcon(name: String): Int {
        return when(name.lowercase()) {
            "large" -> R.drawable.ic_coffee_large
            "small" -> R.drawable.ic_coffee_small
            else -> R.drawable.ic_coffee_medium
        }
    }

    fun getCoffeeSize(sizeId: String): CoffeeSize {
        return sizes?.find { it.id == sizeId } ?: error("Size not found!")
    }
}

data class SizeListItem(val id: String, @DrawableRes val icon: Int, val name: String)