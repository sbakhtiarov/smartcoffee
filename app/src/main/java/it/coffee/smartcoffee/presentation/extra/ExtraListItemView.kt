package it.coffee.smartcoffee.presentation.extra

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import it.coffee.smartcoffee.R

class ExtraListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val title: TextView by lazy { findViewById(R.id.text_title) }
    private val divider: View by lazy { findViewById(R.id.divider) }
    private val choices: LinearLayout by lazy { findViewById(R.id.choices_view) }

    private var currentItem: ExtraListItem? = null

    var callback: ((extraId: String, choiceId: String) -> Unit)? = null

    var isExpanded: Boolean = false
        set(value) {
            if (value != field) {
                field = value

                if (field) {
                    expand()
                } else {
                    collapse()
                }
            }
        }

    private fun collapse() {
        divider.isVisible = false
        choices.isVisible = false
    }

    private fun expand() {
        divider.isVisible = true
        choices.isVisible = true
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        setOnClickListener {
            isExpanded = !isExpanded
        }
    }

    fun bind(item: ExtraListItem, payloads: List<Any?>) {
        if (payloads.isNotEmpty()) {
            currentItem = item

            item.choices.forEachIndexed { index, choiceItem ->
                val choiceView = choices.getChildAt(index)
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

        choices.removeAllViews()

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

            choices.addView(choiceView)
        }
    }

    private fun onSelection(id: String) {
        currentItem?.let { cItem ->
            val choice = cItem.choices.find { it.id == id } ?: return
                if (!choice.selected) {
                    callback?.invoke(cItem.id, choice.id)
                }
        }

//        currentItem?.let { cItem ->
//
//            val choiceItems = HashSet<ExtraChoiceItem>()
//
//            cItem.choices.forEachIndexed { index, item ->
//
//                val choiceView = choices.getChildAt(index)
//                val extraIcon = choiceView.findViewById<ImageView>(R.id.image1)
//
//                if (item.id == id) {
//                    if (item.selected) {
////                        extraIcon.setImageResource(R.drawable.ic_extra_choice)
//                        choiceItems.add(item.copy(selected = true))
//                    } else {
//                        extraIcon.setImageResource(R.drawable.ic_extra_choice_selected)
//                        choiceItems.add(item.copy(selected = true))
//                    }
//                } else {
//                    extraIcon.setImageResource(R.drawable.ic_extra_choice)
//                    choiceItems.add(item.copy(selected = false))
//                }
//            }
//
//            currentItem = cItem.copy(choices = choiceItems)
//        }
//
//
//        postDelayed(500) {
//            isExpanded = false
 //        }

    }

}