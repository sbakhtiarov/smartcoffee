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

        with (layoutInflater.inflate(R.layout.list_item, summaryView, false) as TextView) {
            text = coffee.style.name
            setCompoundDrawablesWithIntrinsicBounds(CoffeeUtils.getStyleIcon(coffee.style.id), 0, 0, 0)
            summaryView.addView(this)
        }

        coffee.size?.let { size ->
            with (layoutInflater.inflate(R.layout.list_item, summaryView, false) as TextView) {
                text = size.name
                setCompoundDrawablesWithIntrinsicBounds(CoffeeUtils.getSizeIcon(size.id), 0, 0, 0)
                summaryView.addView(this)
            }
        }

        coffee.extra.forEach { extra ->

            val extraView = LinearLayout(requireContext())
            extraView.orientation = LinearLayout.VERTICAL
            extraView.showDividers = LinearLayout.SHOW_DIVIDER_NONE

            with (layoutInflater.inflate(R.layout.list_item, summaryView, false) as TextView) {
                text = extra.name
                setCompoundDrawablesWithIntrinsicBounds(CoffeeUtils.getExtraIcon(extra.id), 0, 0, 0)
                extraView.addView(this)
            }

            with (layoutInflater.inflate(R.layout.extra_choice_item, summaryView, false)) {
                findViewById<TextView>(R.id.text1).text = extra.subselections[0].name
                findViewById<ImageView>(R.id.image1).setImageResource(R.drawable.ic_extra_choice_selected)

                val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt()
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.setMargins(margin, 0, margin, margin)
                extraView.addView(this, lp)
            }

            summaryView.addView(extraView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        }

        view.findViewById<View>(R.id.button_done).setOnClickListener {
            mainViewModel.confirmCoffee()
        }
    }

}