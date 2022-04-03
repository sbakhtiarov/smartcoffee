package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_types")
data class CoffeeTypeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val sizes: List<String>?,
    val extras: List<String>?,
)
