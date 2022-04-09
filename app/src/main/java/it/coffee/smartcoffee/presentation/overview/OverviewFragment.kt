package it.coffee.smartcoffee.presentation.overview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.fragment.app.Fragment
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.ExtraChoiceItemBinding
import it.coffee.smartcoffee.databinding.OverviewFragmentBinding
import it.coffee.smartcoffee.presentation.CoffeeUtils
import it.coffee.smartcoffee.presentation.main.MainViewModel
import it.coffee.smartcoffee.util.pxToDp
import it.coffee.smartcoffee.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OverviewFragment : Fragment(R.layout.overview_fragment) {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val binding by viewBinding(OverviewFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffee = requireNotNull(mainViewModel.coffee) { "Coffee not selected" }

        addListItem(binding.summaryView,
            coffee.style.name,
            CoffeeUtils.getStyleIcon(coffee.style.id))

        coffee.size?.let { size ->
            addListItem(binding.summaryView, size.name, CoffeeUtils.getSizeIcon(size.id))
        }

        coffee.extra.forEach { extra ->

            val extraView = LinearLayout(requireContext())
            extraView.orientation = LinearLayout.VERTICAL
            extraView.showDividers = LinearLayout.SHOW_DIVIDER_NONE

            addListItem(extraView, extra.name, CoffeeUtils.getExtraIcon(extra.id))
            addExtraListItem(extraView, extra.subselections[0].name)

            binding.summaryView.addView(extraView,
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        binding.buttonDone.setOnClickListener {
            mainViewModel.confirmCoffee()
        }
    }

    private fun addListItem(parent: ViewGroup, text: String, icon: Int) {
        with(layoutInflater.inflate(R.layout.list_item, parent, false) as TextView) {
            this.text = text
            setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
            parent.addView(this)
        }
    }

    @Suppress("MagicNumber")
    private fun addExtraListItem(parent: ViewGroup, text: String) {
        with(ExtraChoiceItemBinding.inflate(layoutInflater, parent, false)) {

            textTitle.text = text
            imageCheck.setImageResource(R.drawable.ic_extra_choice_selected)

            val margin = pxToDp(24f)

            val lp = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
                setMargins(margin, 0, margin, margin)
            }

            parent.addView(root, lp)
        }
    }

}