package it.coffee.smartcoffee.data.database.dao

import androidx.room.*
import it.coffee.smartcoffee.data.database.entity.*

@Dao
interface CoffeeMachineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachine(coffeeMachineEntity: CoffeeMachineEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineTypes(types: List<CoffeeTypeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineSizes(sizes: List<CoffeeSizeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineExtras(extras: List<CoffeeExtraEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineTypesMap(types: List<CoffeeMachineTypesMap>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineSizesMap(sizes: List<CoffeeMachineSizesMap>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoffeeMachineExtrasMap(extras: List<CoffeeMachineExtrasMap>)

    @Transaction
    @Query("SELECT * FROM coffee_machine WHERE id = :machine_id")
    suspend fun getCoffeeMachine(machine_id: String): CoffeeMachineJoinedEntity

    @Query("SELECT * FROM coffee_types WHERE id IN (SELECT types_map.type_id FROM types_map WHERE types_map.machine_id = :machine_id)")
    suspend fun getTypes(machine_id: String): List<CoffeeTypeEntity>

    @Query("SELECT * FROM coffee_sizes WHERE id IN (SELECT sizes_map.size_id FROM sizes_map WHERE sizes_map.machine_id = :machine_id)")
    suspend fun getSizes(machine_id: String): List<CoffeeSizeEntity>

    @Query("SELECT * FROM coffee_extras WHERE id IN (SELECT extras_map.extra_id FROM extras_map WHERE extras_map.machine_id = :machine_id)")
    suspend fun getExtras(machine_id: String): List<CoffeeExtraEntity>
}