package it.coffee.smartcoffee.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import it.coffee.smartcoffee.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launchWhenStarted {
            viewModel.navigate.observe(this@MainActivity) { destination ->
                destination?.let {
                    findNavController(R.id.nav_host_fragment).navigate(it)
                    viewModel.onNavigateComplete()
                }
            }
        }
    }
}