package it.coffee.smartcoffee.presentation.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExtraFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: ExtraViewModel by viewModel {
        parametersOf(mainViewModel.coffee?.style ?: error("No style selected"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.extra_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.text_extras)

        viewModel.extras.observe(viewLifecycleOwner) { extras ->
            extras?.let {
//                textView.text = extras.joinToString(separator = "\n") { it.toString() }
                val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                textView.text = gson.toJson(extras).toString()
            }
        }

        view.findViewById<View>(R.id.button_next).setOnClickListener {
            val extras = viewModel.extras.value?.map {
                it.copy(subselections = listOf(it.subselections[0]))
            }
            mainViewModel.setExtras(extras ?: emptyList())
        }
    }
}