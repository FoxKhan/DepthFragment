package com.developer.smartfox.fragmentdemo.common

import android.view.animation.Interpolator
import kotlin.math.pow

class CustomInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float =
        (input - 1).pow(5) + 1
}