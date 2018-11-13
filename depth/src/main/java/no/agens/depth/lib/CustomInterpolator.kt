package no.agens.depth.lib

import android.view.animation.Interpolator
import kotlin.math.pow

class CustomInterpolator : Interpolator {
    override fun getInterpolation(input: Float): Float =
        (input - 1).pow(5) + 1
}