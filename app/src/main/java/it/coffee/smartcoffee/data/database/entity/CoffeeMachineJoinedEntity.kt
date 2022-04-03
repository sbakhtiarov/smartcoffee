package it.coffee.smartcoffee.data.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CoffeeMachineJoinedEntity(

    @Embedded
    val coffeeMachine: CoffeeMachineEntity,

    @Relation(
        entity = CoffeeTypeEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CoffeeMachineTypesMap::class,
            parentColumn = "machine_id",
            entityColumn = "type_id")
    )
    val types: List<CoffeeTypeEntity>,

    @Relation(
        entity = CoffeeSizeEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CoffeeMachineSizesMap::class,
            parentColumn = "machine_id",
            entityColumn = "size_id")
    )
    val sizes: List<CoffeeSizeEntity>,

    @Relation(
        entity = CoffeeExtraEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CoffeeMachineExtrasMap::class,
            parentColumn = "machine_id",
            entityColumn = "extra_id")
    )
    val extras: List<CoffeeExtraEntity>,

)
