package it.coffee.smartcoffee.presentation.overview

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.CoffeeUtils
import it.coffee.smartcoffee.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OverviewFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffee = requireNotNull(mainViewModel.coffee) { "Coffee not selected" }
        val summaryView = view.findViewById<LinearLayout>(R.id.summary_view)

        addListItem(summaryView, coffee.style.name, CoffeeUtils.getStyleIcon(coffee.style.id))

        coffee.size?.let { size ->
            addListItem(summaryView, size.name, CoffeeUtils.getSizeIcon(size.id))
        }

        coffee.extra.forEach { extra ->

            val extraView = LinearLayout(requireContext())
            extraView.orientation = LinearLayout.VERTICAL
            extraView.showDividers = LinearLayout.SHOW_DIVIDER_NONE

            addListItem(extraView, extra.name, CoffeeUtils.getExtraIcon(extra.id))
            addExtraListItem(extraView, extra.subselections[0].name)

            summaryView.addView(extraView,
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        view.findViewById<View>(R.id.button_done).setOnClickListener {
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

    private fun addExtraListItem(parent: ViewGroup, text: String) {
        with(layoutInflater.inflate(R.layout.extra_choice_item, parent, false)) {
            findViewById<TextView>(R.id.text1).text = text
            findViewById<ImageView>(R.id.image1).setImageResource(R.drawable.ic_extra_choice_selected)

            val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                24f,
                resources.displayMetrics).toInt()
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(margin, 0, margin, margin)
            parent.addView(this, lp)
        }
    }

}