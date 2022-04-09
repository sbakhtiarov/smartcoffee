package it.coffee.smartcoffee.presentation.main

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import it.coffee.smartcoffee.R
import kotlinx.coroutines.launch
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

            viewModel.enableNfc.observe(this@MainActivity) { enable ->
                lifecycleScope.launchWhenResumed {
                    if (enable) {
                        enableNfc()
                    } else {
                        disableNfc()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        disableNfc()
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            viewModel.resetConnection()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            intent.getParcelableExtra<Tag?>(NfcAdapter.EXTRA_TAG)?.let {
                viewModel.onNfcTagRead(it)
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun enableNfc() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val tag = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val intentFiltersArray = arrayOf(tag)

        val techListsArray = arrayOf(arrayOf<String>(
            NfcA::class.java.name,
            MifareClassic::class.java.name,
            MifareUltralight::class.java.name,
        ))

        NfcAdapter.getDefaultAdapter(this)
            .enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)

        Log.d("NFC", "Foreground dispatch enabled")
    }

    private fun disableNfc() {
        Log.d("NFC", "Foreground dispatch disabled")
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this)
    }

}
