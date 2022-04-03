package it.coffee.smartcoffee.domain.model

import com.google.gson.annotations.SerializedName

data class CoffeeExtra(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val subselections: List<ExtraChoice>,
)