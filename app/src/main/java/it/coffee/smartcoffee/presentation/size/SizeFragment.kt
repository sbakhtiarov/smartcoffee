package it.coffee.smartcoffee.presentation.size

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SizeFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: SizeViewModel by viewModel {
        parametersOf(
            mainViewModel.coffee?.style ?: error("No style selected"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.size_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.text_sizes)

        viewModel.sizes.observe(viewLifecycleOwner) { sizes ->
            sizes?.let {
                textView.text = sizes.joinToString(separator = "\n") { it.name }
            }
        }

        view.findViewById<View>(R.id.button_next).setOnClickListener {
            viewModel.sizes.value?.get(0)?.let {
                mainViewModel.setSize(it)
            }
        }

    }

}