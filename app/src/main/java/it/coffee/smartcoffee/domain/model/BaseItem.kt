package it.coffee.smartcoffee.domain.model

import com.google.gson.annotations.SerializedName

typealias CoffeeSize = BaseItem
typealias ExtraChoice = BaseItem

data class BaseItem(@SerializedName("_id") val id: String, val name: String)
