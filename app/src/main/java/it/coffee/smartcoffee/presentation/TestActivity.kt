package it.coffee.smartcoffee.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.data.CoffeeRepositoryImpl
import it.coffee.smartcoffee.data.database.DatabaseDataSourceImpl
import it.coffee.smartcoffee.data.network.NetworkDataSourceImpl
import it.coffee.smartcoffee.domain.Failure
import it.coffee.smartcoffee.domain.Success
import kotlinx.coroutines.Dispatchers

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val repository = CoffeeRepositoryImpl(
            DatabaseDataSourceImpl(),
            NetworkDataSourceImpl(),
            Dispatchers.IO
        )

        findViewById<Button>(R.id.button_test).setOnClickListener {

            lifecycleScope.launchWhenStarted {

                val result = repository.getMachineInfo("60ba1ab72e35f2d9c786c610")

                when (result) {
                    is Success -> Log.d("Result", result.toString())
                    is Failure -> Log.e("Error", result.exception.localizedMessage, result.exception)
                }
            }

        }
    }
}