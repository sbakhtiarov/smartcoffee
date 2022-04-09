package it.coffee.smartcoffee.presentation.size

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.SizeFragmentBinding
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SizeFragment : Fragment(R.layout.size_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: SizeViewModel by viewModel {
        parametersOf(
            requireNotNull(mainViewModel.coffee?.style) { "No style selected" })
    }
    private val binding by viewBinding(SizeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        lifecycleScope.launchWhenStarted {
            viewModel.items.observe(viewLifecycleOwner) { items ->
                items?.let {
                    binding.list.adapter = SizesAdapter(items) {
                        mainViewModel.setSize(viewModel.getCoffeeSize(it.id))
                    }
                }
            }
        }
    }
}