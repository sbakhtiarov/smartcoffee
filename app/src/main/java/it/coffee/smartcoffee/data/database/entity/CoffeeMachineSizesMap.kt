package it.coffee.smartcoffee.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "sizes_map",
    primaryKeys = ["machine_id", "size_id"],
    indices = [Index(value = ["size_id"], unique = true)],
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
    @ColumnInfo(name = "machine_id")
    val machineId: String,
    @ColumnInfo(name = "size_id")
    val sizeId: String
)