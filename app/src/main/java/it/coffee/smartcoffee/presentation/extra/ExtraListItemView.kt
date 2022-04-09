package it.coffee.smartcoffee.presentation.extra

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.core.view.postDelayed
import it.coffee.smartcoffee.R
import it.coffee.smartcoffee.databinding.ExtraChoiceItemBinding
import it.coffee.smartcoffee.databinding.ExtraListItemBinding
import it.coffee.smartcoffee.presentation.widget.ExpandableListItemView

@Suppress("MagicNumber")
class ExtraListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ExpandableListItemView(context, attrs) {

    override val collapseAnimationStartOffset = 150L
    override val expandInterpolator = OvershootInterpolator()

    private lateinit var binding: ExtraListItemBinding

    private var currentItem: ExtraListItem? = null

    var callback: ((extraId: String, choiceId: String) -> Unit)? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ExtraListItemBinding.bind(this)
    }

    override fun animateCollapse() {

        binding.divider.animate().apply {
            duration = 200
            alpha(0f)
        }

        binding.choicesList.animate().apply {
            duration = 200
            alpha(0f)
        }

        super.animateCollapse()
    }

    override fun animateExpand() {

        binding.divider.alpha = 0f
        binding.divider.animate().apply {
            startDelay = 150
            duration = 300
            alpha(1f)
        }

        binding.choicesList.alpha = 0f
        binding.choicesList.animate().apply {
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
                val choiceView = binding.choicesList.getChildAt(index)
                val extraIcon = choiceView.findViewById<ImageView>(R.id.image_check)

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

        binding.textTitle.text = item.name
        binding.textTitle.setCompoundDrawablesWithIntrinsicBounds(item.icon, 0, 0, 0)

        binding.choicesList.removeAllViews()

        val inflater = LayoutInflater.from(context)

        currentItem?.choices?.forEach {

            val choiceView = ExtraChoiceItemBinding.inflate(inflater, binding.choicesList, false)

            choiceView.textTitle.text = it.name

            if (it.selected) {
                choiceView.imageCheck.setImageResource(R.drawable.ic_extra_choice_selected)
            } else {
                choiceView.imageCheck.setImageResource(R.drawable.ic_extra_choice)
            }

            choiceView.root.setOnClickListener { _ ->
                onSelection(it.id)
            }

            binding.choicesList.addView(choiceView.root)
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