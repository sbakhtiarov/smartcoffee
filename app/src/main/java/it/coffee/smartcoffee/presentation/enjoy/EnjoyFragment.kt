package it.coffee.smartcoffee.presentation.enjoy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.EnjoyFragmentBinding
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EnjoyFragment : Fragment(R.layout.enjoy_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val binding by viewBinding(EnjoyFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })

        binding.buttonNewCoffee.setOnClickListener {
            mainViewModel.restartCoffeeBuilder()
        }

    }

}