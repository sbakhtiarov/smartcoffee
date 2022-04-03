package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coffee_machine")
data class CoffeeMachineEntity(
    @PrimaryKey val id: String,
)
