package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "extras_map",
    primaryKeys = ["machine_id", "extra_id"],
    indices = [Index(value = ["extra_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CoffeeMachineEntity::class,
            parentColumns = ["id"],
            childColumns = ["machine_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CoffeeExtraEntity::class,
            parentColumns = ["id"],
            childColumns = ["extra_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class CoffeeMachineExtrasMap(
    val machine_id: String,
    val extra_id: String
)