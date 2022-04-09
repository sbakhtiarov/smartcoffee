package it.coffee.smartcoffee.presentation.extra

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.ExtraFragmentBinding
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ExtraFragment : Fragment(R.layout.extra_fragment) {

    private val binding by viewBinding(ExtraFragmentBinding::bind)

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: ExtraViewModel by viewModel {
        parametersOf(requireNotNull(mainViewModel.coffee?.style) { "No style selected" })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonDone.setOnClickListener {
            mainViewModel.setExtras(viewModel.getChoices())
        }

        binding.list.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExtraListAdapter { extraId, choiceId ->
           viewModel.onChoice(extraId, choiceId)
        }
        binding.list.adapter = adapter
        binding.list.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        lifecycleScope.launchWhenStarted {
            viewModel.items.observe(viewLifecycleOwner) { items ->
                items?.let {
                    adapter.submitList(items)
                }
            }

            viewModel.showNext.observe(viewLifecycleOwner) { showNext ->
                binding.buttonDone.isVisible = showNext ?: false
            }
        }
    }
}
