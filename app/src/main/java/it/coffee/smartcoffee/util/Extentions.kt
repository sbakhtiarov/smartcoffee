package it.coffee.smartcoffee.util

import android.util.TypedValue
import androidx.fragment.app.Fragment

fun Fragment.pxToDp(value: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        value, resources.displayMetrics).toInt()
}
