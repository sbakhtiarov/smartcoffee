package it.coffee.smartcoffee.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.withTransaction
import it.coffee.smartcoffee.data.database.entity.*
import it.coffee.smartcoffee.domain.DatabaseDataSource
import it.coffee.smartcoffee.domain.Result
import it.coffee.smartcoffee.domain.Success
import it.coffee.smartcoffee.domain.UnknownError
import it.coffee.smartcoffee.domain.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.IllegalStateException
import java.util.NoSuchElementException

class DatabaseDataSourceImpl(context: Context) : DatabaseDataSource {

    private val db: CoffeeMachineDatabase = Room.databaseBuilder(
        context,
        CoffeeMachineDatabase::class.java,
        "coffee_machine_db")
        .build()

    override suspend fun getMachineInfo(machine_id: String): Result<CoffeeMachineInfo> {

        return try {

            val entity = db.coffeeMachineDao().getCoffeeMachine(machine_id)

            val info = CoffeeMachineInfo(
                id = entity.coffeeMachine.id,
                types = entity.types.map {
                    CoffeeType(
                        id = it.id,
                        name = it.name,
                        extras = it.extras,
                        sizes = it.sizes
                    )
                },
                sizes = entity.sizes.map {
                    CoffeeSize(it.id, it.name)
                },
                extras = entity.extras.map {
                    CoffeeExtra(it.id, it.name, it.subselections)
                }
            )

            Success(info)

        } catch (e: Throwable) {
            UnknownError(e)
        }


    }

    override suspend fun putMachineInfo(info: CoffeeMachineInfo) {

        db.withTransaction {
            with(db.coffeeMachineDao()) {

                insertCoffeeMachine(CoffeeMachineEntity(info.id))

                insertCoffeeMachineTypes(info.types.map {
                    CoffeeTypeEntity(it.id, it.name, it.sizes, it.extras)
                })

                insertCoffeeMachineTypesMap(info.types.map {
                    CoffeeMachineTypesMap(info.id, it.id)
                })

                insertCoffeeMachineSizes(info.sizes.map {
                    CoffeeSizeEntity(it.id, it.name)
                })

                insertCoffeeMachineSizesMap(info.sizes.map {
                    CoffeeMachineSizesMap(info.id, it.id)
                })

                insertCoffeeMachineExtras(info.extras.map {
                    CoffeeExtraEntity(it.id, it.name, it.subselections)
                })

                insertCoffeeMachineExtrasMap(info.extras.map {
                    CoffeeMachineExtrasMap(info.id, it.id)
                })
            }
        }
    }

    override suspend fun getTypes(machine_id: String): Result<List<CoffeeType>> {
        return try {
            val types = db.coffeeMachineDao().getTypes(machine_id).map {
                CoffeeType(it.id, it.name, it.sizes, it.extras)
            }
            Success(types)
        } catch (e: Throwable) {
            UnknownError(e)
        }
    }

    override suspend fun getSizes(sizeIds: List<String>): Result<List<CoffeeSize>> {
        return try {
            val sizes = db.coffeeMachineDao().getSizes(sizeIds).map {
                CoffeeSize(it.id, it.name)
            }
            Success(sizes)
        } catch (e: Throwable) {
            UnknownError(e)
        }
    }

    override suspend fun getExtras(extraIds: List<String>): Result<List<CoffeeExtra>> {
        return try {
            val types = db.coffeeMachineDao().getExtras(extraIds).map {
                CoffeeExtra(it.id, it.name, it.subselections)
            }
            Success(types)
        } catch (e: Throwable) {
            UnknownError(e)
        }
    }

    override suspend fun getRecentCoffee(machine_id: String): Result<Coffee> {

        return try {

            val entity = db.coffeeMachineDao().getRecentCoffee(machine_id)

            if (entity != null) {
                coroutineScope {

                    val typeEntity = async { db.coffeeMachineDao().getStyle(entity.style_id) }
                    val sizeEntity = async { db.coffeeMachineDao().getSize(entity.size_id) }

                    val style = with(typeEntity.await()) {
                        CoffeeType(id, name, sizes, extras)
                    }

                    val size = with(sizeEntity.await()) {
                        CoffeeSize(id, name)
                    }

                    Success(Coffee(style, size, entity.choices))
                }
            } else {
                UnknownError(NoSuchElementException("No recent coffee"))
            }

        } catch (e: Throwable) {
            UnknownError(e)
        }
    }

    override suspend fun putRecentCoffee(machine_id: String, coffee: Coffee) {
        db.coffeeMachineDao().insertRecentCoffee(RecentCoffeeEntity(
            machine_id,
            coffee.style.id,
            coffee.size?.id ?: "",
            coffee.extra
        ))
    }
}