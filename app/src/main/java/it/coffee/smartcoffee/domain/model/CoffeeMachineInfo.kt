package it.coffee.smartcoffee.domain.model

import com.google.gson.annotations.SerializedName

data class CoffeeMachineInfo(
    @SerializedName("_id")
    val id: String,
    val types: List<CoffeeType>,
    val sizes: List<CoffeeSize>,
    val extras: List<CoffeeExtra>,
)
