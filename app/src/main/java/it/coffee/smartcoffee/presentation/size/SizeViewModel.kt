package it.coffee.smartcoffee.presentation.size

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import it.coffee.smartcoffee.presentation.CoffeeUtils
import kotlinx.coroutines.launch

class SizeViewModel(private val style: CoffeeType, private val repository: CoffeeRepository) : ViewModel() {

    private val _items = MutableLiveData<List<SizeListItem>>()
    val items : LiveData<List<SizeListItem>> = _items

    private var sizes: List<CoffeeSize>? = null

    init {
        viewModelScope.launch {
            when (val result = repository.getSizes(style.sizes ?: error("No sizes defined"))) {
                is Success -> {
                    sizes = result.value
                    _items.value = sizes?.map {
                        SizeListItem(it.id, CoffeeUtils.getSizeIcon(it.id), it.name)
                    }
                }
                is Failure -> error(result.exception)
            }
        }
    }

    fun getCoffeeSize(sizeId: String): CoffeeSize {
        return sizes?.find { it.id == sizeId } ?: error("Size not found!")
    }
}

data class SizeListItem(val id: String, @DrawableRes val icon: Int, val name: String)