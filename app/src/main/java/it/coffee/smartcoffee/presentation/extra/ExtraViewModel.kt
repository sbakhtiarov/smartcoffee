package it.coffee.smartcoffee.presentation.extra

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeType
import it.coffee.smartcoffee.domain.model.ExtraChoice
import it.coffee.smartcoffee.presentation.CoffeeUtils
import kotlinx.coroutines.launch

class ExtraViewModel(
    private val style: CoffeeType,
    private val repository: CoffeeRepository,
    private val mapper: ExtraViewModelMapper = ExtraViewModelMapper(),
) : ViewModel() {

    private val _items = MutableLiveData<List<ExtraListItem>>()
    val items: LiveData<List<ExtraListItem>> = _items

    val showNext: LiveData<Boolean> = items.map { it.allExtrasSelected() }

    private var extras: List<CoffeeExtra>? = null

    init {
        viewModelScope.launch {
            when (val result = repository.getExtras(style.extras ?: error("No extras defined"))) {
                is Success -> {
                    extras = result.value
                    _items.value = result.value.map { mapper.map(it) }
                }
                is Failure -> error(result.exception)
            }
        }
    }

    fun onChoice(extraId: String, choiceId: String) {
        _items.value?.let { oldList ->
            _items.value = buildList {
                oldList.forEach { extra ->
                    if (extra.id == extraId) {
                        add(extra.copy(choices = extra.choices.copy(choiceId)))
                    } else {
                        add(extra)
                    }
                }
            }
        }
    }

    private fun List<ExtraListItem>.allExtrasSelected(): Boolean {
        return all { extra -> extra.choices.any { it.selected } }
    }

    private fun List<ExtraChoiceItem>.copy(selectedId: String): List<ExtraChoiceItem> {
        return buildList {
            this@copy.forEach {
                add(it.copy(selected = it.id == selectedId))
            }
        }
    }

    fun getChoices(): List<CoffeeExtra> {
        return buildList {
            _items.value?.forEach { item ->
                val extra = extras?.find { it.id == item.id } ?: error("Item not found")
                val selectedItem =
                    item.choices.find { it.selected }?.id ?: error("Selected item not found")

                add(extra.copy(subselections = extra.subselections.filter { it.id == selectedItem }))
            }
        }
    }
}

data class ExtraChoiceItem(val id: String, val name: String, val selected: Boolean = false)

data class ExtraListItem(
    val id: String,
    @DrawableRes val icon: Int,
    val name: String,
    val choices: List<ExtraChoiceItem>,
)

class ExtraViewModelMapper {
    fun map(extra: CoffeeExtra) =
        ExtraListItem(extra.id,
            CoffeeUtils.getExtraIcon(extra.id),
            extra.name,
            extra.subselections.map { choice ->
                map(choice)
            })

    private fun map(choice: ExtraChoice) = ExtraChoiceItem(choice.id, choice.name)
}
