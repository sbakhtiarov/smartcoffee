package it.coffee.smartcoffee.presentation.style

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StyleFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: StyleViewModel by viewModel {
        parametersOf(mainViewModel.machineId ?: error("No machine id"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.style_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView = view.findViewById<TextView>(R.id.text_styles)

        lifecycleScope.launchWhenStarted {
            viewModel.styles.observe(viewLifecycleOwner) { styles ->
                styles?.let {
                    textView.text = styles.joinToString(separator = "\n") { it.name }
                }
            }
        }

        view.findViewById<View>(R.id.button_next).setOnClickListener {
            viewModel.styles.value?.get(2)?.let {
                mainViewModel.setStyle(it)
            }
        }

    }

}