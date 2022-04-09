package it.coffee.smartcoffee.presentation.enjoy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EnjoyFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.enjoy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })

        view.findViewById<View>(R.id.button_new_coffee).setOnClickListener {
            mainViewModel.restartCoffeeBuilder()
        }

    }

}