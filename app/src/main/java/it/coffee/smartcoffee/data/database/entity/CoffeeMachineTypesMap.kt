package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "types_map",
    primaryKeys = ["machine_id", "type_id"],
    indices = [Index(value = ["type_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CoffeeMachineEntity::class,
            parentColumns = ["id"],
            childColumns = ["machine_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CoffeeTypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class CoffeeMachineTypesMap(
    val machine_id: String,
    val type_id: String
)