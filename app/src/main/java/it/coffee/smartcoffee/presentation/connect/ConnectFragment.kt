package it.coffee.smartcoffee.presentation.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.ConnectFragmentBinding
import it.coffee.smartcoffee.domain.NetworkError
import it.coffee.smartcoffee.domain.UnknownError
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConnectFragment : Fragment(R.layout.connect_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: ConnectViewModel by viewModel()

    private val binding by viewBinding(ConnectFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Waiting -> {
                    view.setOnClickListener {
                        viewModel.getMachineInfo("60ba1ab72e35f2d9c786c610") // Hardcoded test machine id
                        view.setOnClickListener(null)
                    }
                    binding.progress.isVisible = false
                    binding.textScreenTap.isVisible = state.showHelp
                }
                Connecting -> {
                    binding.progress.isVisible = true
                    binding.textScreenTap.isVisible = false
                }
                is ConnectionFailure -> {
                    when (state.error) {
                        is NetworkError -> Toast.makeText(requireContext(),
                            getString(R.string.no_connection_error), Toast.LENGTH_SHORT).show()
                        is UnknownError -> Toast.makeText(requireContext(),
                            getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                    viewModel.onErrorShown()
                }
                is ConnectionSuccess -> {
                    binding.progress.isVisible = false
                    binding.textScreenTap.isVisible = false

                    viewModel.onConnectHandled()
                    mainViewModel.onConnected(state.machineInfo)
                }
            }
        }
    }
}