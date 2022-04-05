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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentCoffee(coffee: RecentCoffeeEntity)

    @Query("SELECT * FROM recent_coffee WHERE machine_id = :machine_id")
    suspend fun getRecentCoffee(machine_id: String): RecentCoffeeEntity?

    @Transaction
    @Query("SELECT * FROM coffee_machine WHERE id = :machine_id")
    suspend fun getCoffeeMachine(machine_id: String): CoffeeMachineJoinedEntity

    @Query("SELECT * FROM coffee_types WHERE id IN (SELECT types_map.type_id FROM types_map WHERE types_map.machine_id = :machine_id)")
    suspend fun getTypes(machine_id: String): List<CoffeeTypeEntity>

    @Query("SELECT * FROM coffee_sizes WHERE id IN (:sizes)")
    suspend fun getSizes(sizes: List<String>): List<CoffeeSizeEntity>

    @Query("SELECT * FROM coffee_extras WHERE id IN (:extras)")
    suspend fun getExtras(extras: List<String>): List<CoffeeExtraEntity>

    @Query("SELECT * FROM coffee_types WHERE id = :style_id")
    suspend fun getStyle(style_id: String) : CoffeeTypeEntity

    @Query("SELECT * FROM coffee_sizes WHERE id = :size_id")
    suspend fun getSize(size_id: String) : CoffeeSizeEntity

}