package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_sizes")
data class CoffeeSizeEntity(
    @PrimaryKey val id: String,
    val name: String
)