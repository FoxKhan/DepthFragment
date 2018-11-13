package com.developer.smartfox.fragmentdemo.common

import android.animation.ObjectAnimator
import android.view.View
import com.developer.smartfox.fragmentdemo.R
import com.pspdfkit.labs.vangogh.api.AnimationSchedulers
import com.pspdfkit.labs.vangogh.base.AnimationBuilder
import io.reactivex.Completable
import no.agens.depth.lib.DepthLayout
import kotlin.random.Random

object KotlinTransitionHelper {

    private const val START_SCALE = 1f
    private const val START_ROTATION = 0f
    private const val START_ROTATION_X = 0f

    private const val TARGET_SCALE = 0.5f
    private const val TARGET_ROTATION = -50f
    private const val TARGET_ROTATION_X = 60f

    private const val MOVE_Y_STEP = 8L
    private const val ELEVATION_STEP = 3L
    private const val DELAY_STEP = 50L


    private const val DURATION = 1100
    private val VALUE_INTERPOLATOR = CustomInterpolator()
    private val INTERPOLATOR = CustomInterpolator()


    fun startMenuAnimate(root: View, fragmentNumber: Int): Completable {

        return menuAnimate(
            root.findViewById<View>(R.id.fragment_root) as DepthLayout,
            0f + fragmentNumber * MOVE_Y_STEP,
            10f + fragmentNumber * ELEVATION_STEP,
            Random.nextLong(0, 100) + (fragmentNumber) * DELAY_STEP,
            Random.nextLong(0, 50),
            TARGET_SCALE,
            TARGET_ROTATION,
            TARGET_ROTATION_X
        )
    }

    fun startRevertFromMenu(root: View, fCount: Int, fragmentNumber: Int): Completable {

        val delay = if (fCount >= 1) Random.nextLong(0, 50) + (fCount - fragmentNumber) * 25
        else Random.nextLong(0, 50)

        return menuAnimate(
            root.findViewById<View>(R.id.fragment_root) as DepthLayout,
            0f,
            10F,
            delay,
            Random.nextLong(0, 25),
            START_SCALE,
            START_ROTATION,
            START_ROTATION_X
        )
    }

    private fun menuAnimate(
        target: DepthLayout,
        moveY: Float,
        customElevation: Float,
        delay: Long,
        subtractDelay: Long,
        scale: Float,
        rotation: Float,
        rotationX: Float
    ): Completable {
        val dp = target.resources.displayMetrics.density

        target.pivotY = (getDistanceToCenterY(target) * 1.7).toFloat()
        target.pivotX = (getDistanceToCenterX(target) * 1.6).toFloat()
        target.cameraDistance = 10000 * dp

        val animation = AnimationBuilder.forView(target)
            .rotationX(rotationX)
            .scaleX(scale)
            .scaleY(scale)
            .duration(DURATION.toLong())
            .interpolator(INTERPOLATOR)
            .buildCompletable()

        val rotationAnim = AnimationBuilder.forView(target)
            .rotation(rotation) //one line
            .duration(1500L)
            .startDelay(delay)
            .interpolator(INTERPOLATOR)
            .buildCompletable()

        val translationY = AnimationBuilder.forView(target)
            .translationY(-moveY * dp * 3) // one line
            .duration(subtractDelay)
            .startDelay(delay)
            .interpolator(INTERPOLATOR)
            .buildCompletable()


        val elevation = ObjectAnimator.ofFloat(
            target,
            DepthLayout.PROPERTY_CUSTOM_SHADOW_ELEVATION,
            target.customShadowElevation,
            customElevation * dp
        )
            .setDuration(DURATION.toLong())

        elevation.run {
            interpolator = VALUE_INTERPOLATOR
            startDelay = delay
            start()
        }

        return AnimationSchedulers.together(animation, translationY, rotationAnim)
    }

    private fun getDistanceToCenterY(target: View): Float {
        val viewCenter = target.top + target.height / 2f
        val rootCenter = ((target.parent as View).height / 2).toFloat()
        return target.height / 2f + rootCenter - viewCenter
    }

    private fun getDistanceToCenterX(target: View): Float {
        val viewCenter = target.left + target.width / 2f
        val rootCenter = ((target.parent as View).width / 2).toFloat()
        return target.width / 2f + rootCenter - viewCenter
    }
}