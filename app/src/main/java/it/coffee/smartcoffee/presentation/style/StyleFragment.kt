package it.coffee.smartcoffee.presentation.style

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.StyleFragmentBinding
import it.coffee.smartcoffee.presentation.CoffeeUtils
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StyleFragment : Fragment(R.layout.style_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: StyleViewModel by viewModel {
        parametersOf(requireNotNull(mainViewModel.machineId) { "No machine id" })
    }
    private val binding by viewBinding(StyleFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        lifecycleScope.launchWhenStarted {
            viewModel.items.observe(viewLifecycleOwner) { styles ->
                styles?.let {
                    binding.list.adapter = StylesAdapter(styles) {
                        mainViewModel.setStyle(viewModel.getCoffeeType(it.id))
                    }
                }
            }

            viewModel.recent.observe(viewLifecycleOwner) {
                it?.let { coffee ->
                    binding.coffeeView.text = coffee.style.name
                    binding.coffeeView.setCompoundDrawablesWithIntrinsicBounds(
                        CoffeeUtils.getStyleIcon(coffee.style.id), 0, 0, 0)

                    binding.coffeeView.setOnClickListener {
                        mainViewModel.repeatCoffee(coffee)
                    }

                    binding.recentCoffeeView.isVisible = true
                }
            }
        }
    }
}