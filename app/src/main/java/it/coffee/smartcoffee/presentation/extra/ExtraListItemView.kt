package it.coffee.smartcoffee.presentation.extra

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.postDelayed
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.presentation.widget.ExpandableListItemView

@Suppress("MagicNumber")
class ExtraListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ExpandableListItemView(context, attrs) {

    override val collapseAnimationStartOffset = 150L
    override val expandInterpolator = OvershootInterpolator()

    private val title: TextView by lazy { findViewById(R.id.text_title) }
    private val divider: View by lazy { findViewById(R.id.divider) }
    private val choicesList: LinearLayout by lazy { findViewById(R.id.choices_list) }

    private var currentItem: ExtraListItem? = null

    var callback: ((extraId: String, choiceId: String) -> Unit)? = null

    override fun animateCollapse() {

        divider.animate().apply {
            duration = 200
            alpha(0f)
        }

        choicesList.animate().apply {
            duration = 200
            alpha(0f)
        }

        super.animateCollapse()
    }

    override fun animateExpand() {

        divider.alpha = 0f
        divider.animate().apply {
            startDelay = 150
            duration = 300
            alpha(1f)
        }

        choicesList.alpha = 0f
        choicesList.animate().apply {
            startDelay = 150
            duration = 300
            alpha(1f)
        }

        super.animateExpand()
    }

    fun bind(item: ExtraListItem, payloads: List<Any?>) {
        if (payloads.isNotEmpty()) {
            currentItem = item

            item.choices.forEachIndexed { index, choiceItem ->
                val choiceView = choicesList.getChildAt(index)
                val extraIcon = choiceView.findViewById<ImageView>(R.id.image1)

                if (choiceItem.selected) {
                    extraIcon.setImageResource(R.drawable.ic_extra_choice_selected)
                } else {
                    extraIcon.setImageResource(R.drawable.ic_extra_choice)
                }
            }

            postDelayed(400) {
                isExpanded = false
            }

        } else {
            bind(item)
        }
    }

    fun bind(item: ExtraListItem) {

        currentItem = item

        title.text = item.name
        title.setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0)

        choicesList.removeAllViews()

        val inflater = LayoutInflater.from(context)

        currentItem?.choices?.forEach {
            val choiceView = inflater.inflate(R.layout.extra_choice_item, this, false)

            val extraText = choiceView.findViewById<TextView>(R.id.text1)
            val extraIcon = choiceView.findViewById<ImageView>(R.id.image1)

            extraText.text = it.name

            if (it.selected) {
                extraIcon.setImageResource(R.drawable.ic_extra_choice_selected)
            } else {
                extraIcon.setImageResource(R.drawable.ic_extra_choice)
            }

            choiceView.setOnClickListener { _ ->
                onSelection(it.id)
            }

            choicesList.addView(choiceView)
        }
    }

    private fun onSelection(id: String) {
        requireNotNull(currentItem).let { item ->
            val choice = item.choices.find { it.id == id } ?: return
            if (!choice.selected) {
                callback?.invoke(item.id, choice.id)
            }
        }
    }

}