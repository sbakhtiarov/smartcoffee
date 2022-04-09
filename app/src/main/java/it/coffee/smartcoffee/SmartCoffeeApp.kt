package it.coffee.smartcoffee

import android.app.Application
import it.coffee.smartcoffee.data.CoffeeRepositoryImpl
import it.coffee.smartcoffee.data.database.DatabaseDataSourceImpl
import it.coffee.smartcoffee.data.network.NetworkDataSourceImpl
import it.coffee.smartcoffee.domain.CoffeeRepository
import it.coffee.smartcoffee.domain.DatabaseDataSource
import it.coffee.smartcoffee.domain.NetworkDataSource
import it.coffee.smartcoffee.presentation.connect.ConnectViewModel
import it.coffee.smartcoffee.presentation.extra.ExtraViewModel
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.presentation.size.SizeViewModel
import it.coffee.smartcoffee.presentation.style.StyleViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@Suppress("unused")
class SmartCoffeeApp : Application() {

    private val appModule = module {

        single<DatabaseDataSource> {
            DatabaseDataSourceImpl(this@SmartCoffeeApp)
        }

        single<NetworkDataSource> {
            NetworkDataSourceImpl()
        }

        single<CoffeeRepository> {
            CoffeeRepositoryImpl(get(), get(), Dispatchers.IO)
        }

        viewModel { MainViewModel(get()) }
        viewModel { ConnectViewModel(get()) }
        viewModel { parameters-> StyleViewModel(machineId = parameters.get(), get()) }
        viewModel { parameters-> SizeViewModel(style = parameters.get(), get()) }
        viewModel { parameters-> ExtraViewModel(style = parameters.get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SmartCoffeeApp)
            modules(appModule)
        }

    }

}