package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import it.coffee.smartcoffee.domain.model.ExtraChoice

@Entity(tableName = "coffee_extras")
data class CoffeeExtraEntity(
    @PrimaryKey val id: String,
    val name: String,
    val subselections: List<ExtraChoice>,
)
