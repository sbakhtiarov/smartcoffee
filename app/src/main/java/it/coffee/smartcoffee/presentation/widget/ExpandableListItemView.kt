package it.coffee.smartcoffee.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

@Suppress("MagicNumber")
open class ExpandableListItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {

    protected open val collapseAnimationStartOffset = 100L
    protected open val collapseInterpolator : Interpolator = LinearInterpolator()
    protected open val expandInterpolator : Interpolator = FastOutSlowInInterpolator()

    var isExpanded: Boolean = false
        set(value) {
            if (value != field) {
                field = value

                if (field) {
                    animateExpand()
                } else {
                    animateCollapse()
                }
            }
        }

    override fun onFinishInflate() {
        super.onFinishInflate()

        assert(childCount == 2) { "ExpandableListItemView must have exactly two children" }

        setOnClickListener {
            isExpanded = !isExpanded
        }
    }

    protected open fun animateCollapse() {

        val contentView = getChildAt(1)
        val actualHeight = contentView.measuredHeight

        val anim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    contentView.isVisible = false
                } else {
                    contentView.updateLayoutParams {
                        height = actualHeight - (actualHeight * interpolatedTime).toInt()
                    }
                    contentView.requestLayout()
                }
            }
        }

        anim.duration = getCollapseAnimationDuration(actualHeight)
        anim.interpolator = collapseInterpolator
        anim.startOffset = collapseAnimationStartOffset
        contentView.startAnimation(anim)
    }

    protected open fun animateExpand() {

        val contentView = getChildAt(1)

        contentView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val actualHeight = contentView.measuredHeight

        contentView.updateLayoutParams { height = 0 }
        contentView.isVisible = true

        val anim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {

                val viewHeight = if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    (actualHeight * interpolatedTime).toInt()
                }

                contentView.updateLayoutParams { height = viewHeight }
                contentView.requestLayout()
            }
        }

        anim.duration = getExpandAnimationDuration(actualHeight)
        anim.interpolator = expandInterpolator
        contentView.startAnimation(anim)
    }

    protected open fun getCollapseAnimationDuration(contentViewHeight: Int): Long {
        return (contentViewHeight / resources.displayMetrics.density).toLong()
    }

    protected open fun getExpandAnimationDuration(contentViewHeight: Int): Long {
        return (contentViewHeight / resources.displayMetrics.density).toLong()
    }

}