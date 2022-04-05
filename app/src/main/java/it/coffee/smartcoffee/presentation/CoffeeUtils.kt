package it.coffee.smartcoffee.presentation

import androidx.annotation.DrawableRes
import it.coffee.smartcoffee.R

object CoffeeUtils {

    @DrawableRes
    fun getStyleIcon(styleId: String): Int {
        return when (styleId) {
            "60be1eabc45ecee5d77ad960" -> R.drawable.ic_cappuccino
            "60be1db3c45ecee5d77ad890" -> R.drawable.ic_espresso
            else -> R.drawable.ic_coffee_medium
        }
    }

    @DrawableRes
    fun getSizeIcon(sizeId: String): Int {
        return when(sizeId) {
            "60ba18d13ca8c43196b5f606" -> R.drawable.ic_coffee_large
            "60ba3368c45ecee5d77a016b" -> R.drawable.ic_coffee_small
            else -> R.drawable.ic_coffee_medium
        }
    }

    @DrawableRes
    fun getExtraIcon(extraId: String): Int {
        return when (extraId) {
            "60ba34a0c45ecee5d77a0263" -> R.drawable.ic_milk
            "60ba197c2e35f2d9c786c525" -> R.drawable.ic_cappuccino
            else -> R.drawable.ic_coffee_medium
        }
    }

}