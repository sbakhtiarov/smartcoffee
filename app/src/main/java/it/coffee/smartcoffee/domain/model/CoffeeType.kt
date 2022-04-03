package it.coffee.smartcoffee.domain.model

import com.google.gson.annotations.SerializedName

data class CoffeeType(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val sizes: List<String>?,
    val extras: List<String>?,
)
