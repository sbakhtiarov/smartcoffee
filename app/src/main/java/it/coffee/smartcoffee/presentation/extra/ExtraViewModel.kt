package it.coffee.smartcoffee.presentation.extra

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.model.CoffeeExtra
import it.coffee.smartcoffee.domain.model.CoffeeType
import it.coffee.smartcoffee.presentation.CoffeeUtils
import kotlinx.coroutines.launch

class ExtraViewModel(private val style: CoffeeType, private val repository: CoffeeRepository) :
    ViewModel() {

    private val _items = MutableLiveData<List<ExtraListItem>>()
    val items: LiveData<List<ExtraListItem>> = _items

    private val _showNext = MutableLiveData(false)
    val showNext: LiveData<Boolean> = _showNext

    private var extras: HashSet<CoffeeExtra>? = null

    init {
        viewModelScope.launch {

            when (val result = repository.getExtras(style.extras ?: error("No extras defined"))) {
                is Success -> {

                    extras = result.value.toHashSet()

                    _items.value = result.value.map {
                        ExtraListItem(it.id, CoffeeUtils.getExtraIcon(it.id), it.name, it.subselections.map { choice ->
                            ExtraChoiceItem(choice.id, choice.name)
                        }.toMutableSet())
                    }

                }
                is Failure -> error(result.exception)
            }
        }
    }

    fun onChoice(extraId: String, choiceId: String) {
        _items.value?.let { oldList ->
            val newList = ArrayList<ExtraListItem>()
            oldList.forEach { extra ->
                if (extra.id == extraId) {
                    val choices = HashSet<ExtraChoiceItem>()
                    extra.choices.forEach { choice ->
                        choices.add(choice.copy(selected = choice.id == choiceId))
                    }
                    newList.add(extra.copy(choices = choices))
                } else {
                    newList.add(extra)
                }
            }

            _items.value = newList
            _showNext.value = newList.all { it.choices.any { it.selected } }
        }
    }

    fun getChoices(): List<CoffeeExtra> {

        val choices = ArrayList<CoffeeExtra>()

        _items.value?.forEach { item ->

            val extra = extras?.find { it.id == item.id } ?: error("Item not found")
            val selectedItem = item.choices.find { it.selected }?.id ?: error("Selected item not found")

            choices.add(extra.copy(subselections = extra.subselections.filter { it.id == selectedItem }))
        }

        return choices
    }
}

data class ExtraChoiceItem(val id: String, val name: String, val selected: Boolean = false)

data class ExtraListItem(
    val id: String,
    @DrawableRes val icon: Int,
    val name: String,
    val choices: MutableSet<ExtraChoiceItem>,
)