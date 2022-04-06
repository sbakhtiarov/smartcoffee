package it.coffee.smartcoffee.presentation.extra

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import it.coffee.smartcoffee.R

class ExtraListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    private val title: TextView by lazy { findViewById(R.id.text_title) }
    private val choicesView: LinearLayout by lazy { findViewById(R.id.choices_view) }
    private val choicesList: LinearLayout by lazy { findViewById(R.id.choices_list) }

    private var currentItem: ExtraListItem? = null

    var callback: ((extraId: String, choiceId: String) -> Unit)? = null

    private var isExpanded: Boolean = false
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

        val actualHeight = choicesView.measuredHeight

        val anim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    choicesView.isVisible = false
                } else {
                    choicesView.updateLayoutParams {
                        height = actualHeight - (actualHeight * interpolatedTime).toInt()
                    }
                    choicesView.requestLayout()
                }
            }
        }

        anim.duration = (actualHeight / resources.displayMetrics.density).toLong()
        anim.startOffset = 100
        choicesView.startAnimation(anim)

        choicesList.animate().apply {
            duration = 200
            alpha(0f)
        }

    }

    private fun expand() {

        choicesView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualHeight = choicesView.measuredHeight

        choicesView.updateLayoutParams { height = 0 }
        choicesView.isVisible = true
        choicesList.alpha = 0f

        val anim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {

                val viewHeight = if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    (actualHeight * interpolatedTime).toInt()
                }

                choicesView.updateLayoutParams { height = viewHeight }
                choicesView.requestLayout()
            }
        }

        anim.duration = (actualHeight / resources.displayMetrics.density).toLong()
        choicesView.startAnimation(anim)

        choicesList.animate().apply {
            startDelay = 100
            duration = 300
            alpha(1f)
        }
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