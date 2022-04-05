package it.coffee.smartcoffee.presentation.connect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
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

//        view.findViewById<View>(R.id.button_connect).setOnClickListener {
//            viewModel.getMachineInfo("60ba1ab72e35f2d9c786c610")
//        }

        val progress = view.findViewById<View>(R.id.progress)
        val tapText = view.findViewById<View>(R.id.text_screen_tap)

        viewModel.connectionState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Waiting -> {
                    view.setOnClickListener {
                        viewModel.getMachineInfo("60ba1ab72e35f2d9c786c610")
                        view.setOnClickListener(null)
                    }
                    progress.isVisible = false
                    tapText.isVisible = state.showHelp
                }
                Connecting -> {
                    progress.isVisible = true
                    tapText.isVisible = false
                }
                is ConnectionFailure -> {
                    when (state.error) {
                        is NetworkError -> Toast.makeText(requireContext(), getString(R.string.no_connection_error), Toast.LENGTH_SHORT).show()
                        is UnknownError -> Toast.makeText(requireContext(), getString(R.string.unknown_error), Toast.LENGTH_SHORT).show()
                    }
                    viewModel.onErrorShown ()
                }
                is ConnectionSuccess -> {
                    mainViewModel.onConnected(state.machineInfo)
                    viewModel.onConnectHandled()
                    progress.isVisible = false
                    tapText.isVisible = false
                }
            }
        }
    }
}