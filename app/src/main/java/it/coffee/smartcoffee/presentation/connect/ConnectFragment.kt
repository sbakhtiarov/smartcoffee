package it.coffee.smartcoffee.presentation.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.domain.NetworkError
import it.coffee.smartcoffee.domain.UnknownError
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: ConnectViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.connect_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button_connect).setOnClickListener {
            viewModel.getMachineInfo("60ba1ab72e35f2d9c786c610")
        }

        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                Waiting -> { }
                Connecting -> { }
                is ConnectionFailure -> {
                    when (state.error) {
                        is NetworkError -> Toast.makeText(requireContext(), "No Internet. Please try later.", Toast.LENGTH_SHORT).show()
                        is UnknownError -> Toast.makeText(requireContext(), "Connection error. Please try later.", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.onErrorShown ()
                }
                is ConnectionSuccess -> {
                    mainViewModel.onConnected(state.machineInfo)
                    viewModel.onConnectHandled()
                }
            }
        }
    }
}