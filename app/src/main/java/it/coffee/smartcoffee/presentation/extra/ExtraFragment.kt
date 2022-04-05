package it.coffee.smartcoffee.presentation.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val buttonDone = view.findViewById<View>(R.id.button_done)

        buttonDone.setOnClickListener {
            mainViewModel.setExtras(viewModel.getChoices())
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ExtraListAdapter() { extraId, choiceId ->
           viewModel.onChoice(extraId, choiceId)
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        lifecycleScope.launchWhenStarted {
            viewModel.items.observe(viewLifecycleOwner) { items ->
                items?.let {
                    adapter.submitList(items)
                }
            }

            viewModel.showNext.observe(viewLifecycleOwner) { showNext ->
                buttonDone.isVisible = showNext ?: false
            }
        }
    }
}