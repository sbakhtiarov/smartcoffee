package it.coffee.smartcoffee.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import it.coffee.smartcoffee.data.database.dao.CoffeeMachineDao
import it.coffee.smartcoffee.data.database.entity.*

@Database(entities = [
    CoffeeExtraEntity::class,
    CoffeeMachineEntity::class,
    CoffeeMachineExtrasMap::class,
    CoffeeMachineSizesMap::class,
    CoffeeMachineTypesMap::class,
    CoffeeSizeEntity::class,
    CoffeeTypeEntity::class
], version = 1, exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class CoffeeMachineDatabase : RoomDatabase() {

    abstract fun coffeeMachineDao() : CoffeeMachineDao

}