package it.coffee.smartcoffee.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "sizes_map",
    primaryKeys = ["machine_id", "size_id"],
    foreignKeys = [
        ForeignKey(
            entity = CoffeeMachineEntity::class,
            parentColumns = ["id"],
            childColumns = ["machine_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CoffeeSizeEntity::class,
            parentColumns = ["id"],
            childColumns = ["size_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class CoffeeMachineSizesMap(
    val machine_id: String,
    val size_id: String
)