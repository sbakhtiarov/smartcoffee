package it.coffee.smartcoffee.presentation.style

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.Coffee
import it.coffee.smartcoffee.domain.model.CoffeeType
import it.coffee.smartcoffee.presentation.CoffeeUtils

class StyleViewModel(
    private val machineId: String,
    private val repository: CoffeeRepository,
    private val mapper: StyleViewModelMapper = StyleViewModelMapper(),
) : ViewModel() {

    val items: LiveData<List<StyleListItem>> = liveData {
        when (val result = repository.getTypes(machineId)) {
            is Success -> {
                styles = result.value
                emit(result.value.map { mapper.map(it) })
            }
            is Failure -> error(result.exception)
        }
    }

    val recent: LiveData<Coffee> = liveData {
        when (val result = repository.getRecentCoffee(machineId)) {
            is Success -> emit(result.value)
            is Failure -> {}
        }
    }

    private var styles: List<CoffeeType>? = null

    fun getCoffeeType(styleId: String): CoffeeType {
        return styles?.find { it.id == styleId } ?: error("Style not found")
    }

}

data class StyleListItem(val id: String, @DrawableRes val icon: Int, val name: String)

class StyleViewModelMapper {
    fun map(style: CoffeeType) =
        StyleListItem(style.id, CoffeeUtils.getStyleIcon(style.id), style.name)
}
