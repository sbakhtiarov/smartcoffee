package it.coffee.smartcoffee.presentation.style

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.CoffeeUtils
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class StyleFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: StyleViewModel by viewModel {
        parametersOf(requireNotNull(mainViewModel.machineId) { "No machine id" })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.style_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recentCoffeeView = view.findViewById<View>(R.id.recent_coffee_view)
        val coffeeView = view.findViewById<TextView>(R.id.coffee_view)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        lifecycleScope.launchWhenStarted {
            viewModel.items.observe(viewLifecycleOwner) { styles ->
                styles?.let {
                    recyclerView.adapter = StylesAdapter(styles) {
                        mainViewModel.setStyle(viewModel.getCoffeeType(it.id))
                    }
                }
            }

            viewModel.recent.observe(viewLifecycleOwner) {
                it?.let { coffee ->
                    coffeeView.text = coffee.style.name
                    coffeeView.setCompoundDrawablesWithIntrinsicBounds(
                        CoffeeUtils.getStyleIcon(coffee.style.id), 0, 0, 0)

                    coffeeView.setOnClickListener {
                        mainViewModel.repeatCoffee(coffee)
                    }

                    recentCoffeeView.isVisible = true
                }
            }
        }
    }
}