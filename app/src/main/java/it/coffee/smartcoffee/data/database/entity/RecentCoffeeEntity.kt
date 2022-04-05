package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import it.coffee.smartcoffee.domain.model.CoffeeExtra

@Entity(tableName = "recent_coffee")
data class RecentCoffeeEntity(
    @PrimaryKey val machine_id: String,
    val style_id: String,
    val size_id: String,
    val choices: List<CoffeeExtra>
)