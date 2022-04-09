package it.coffee.smartcoffee.presentation.size

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeSize
import it.coffee.smartcoffee.domain.model.CoffeeType
import it.coffee.smartcoffee.presentation.CoffeeUtils

class SizeViewModel(
    private val style: CoffeeType,
    private val repository: CoffeeRepository,
    private val mapper: SizeViewModelMapper = SizeViewModelMapper(),
) : ViewModel() {

    private var sizes: List<CoffeeSize>? = null

    val items: LiveData<List<SizeListItem>> = liveData {
        when (val result = repository.getSizes(style.sizes ?: error("No sizes defined"))) {
            is Success -> {
                sizes = result.value
                emit(result.value.map { mapper.map(it) })
            }
            is Failure -> error(result.exception)
        }
    }

    fun getCoffeeSize(sizeId: String): CoffeeSize {
        return sizes?.find { it.id == sizeId } ?: error("Size not found!")
    }
}

data class SizeListItem(val id: String, @DrawableRes val icon: Int, val name: String)

class SizeViewModelMapper {
    fun map(size: CoffeeSize) = SizeListItem(size.id, CoffeeUtils.getSizeIcon(size.id), size.name)
}
