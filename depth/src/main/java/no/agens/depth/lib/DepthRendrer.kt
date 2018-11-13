package no.agens.depth.lib

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

class DepthRendrer : RelativeLayout {

    private val shadowPaint = Paint()
    private var softShadow = resources.getDrawable(R.drawable.shadow, null) as NinePatchDrawable
    private var roundSoftShadow: Drawable = resources.getDrawable(R.drawable.round_soft_shadow, null)

    private val edgePath = Path()

    private var shadowAlpha = 0.3f
        set(shadowAlpha) {
            field = Math.min(1f, Math.max(0f, shadowAlpha))
        }

    private var matrixX = android.graphics.Matrix()

    constructor(context: Context) : super(context) {
        setup()

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup()
    }

    private fun getTopEdgeLength(dl: DepthLayout): Float {
        return getDistance(dl.topLeftBack, dl.topRightBack)
    }

    private fun getDistance(p1: PointF, p2: PointF): Float {
        return Math.sqrt(((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)).toDouble()).toFloat()
    }

    private fun setup() {
        viewTreeObserver.addOnPreDrawListener {
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (child is DepthLayout) {
                    val hasChangedBounds = child.calculateBounds()
                    if (hasChangedBounds)
                        invalidate()
                }
            }
            true
        }

        shadowPaint.color = Color.BLACK
        shadowPaint.isAntiAlias = true

    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        if (child is DepthLayout && !isInEditMode) {


            val src = floatArrayOf(
                0f,
                0f,
                child.width.toFloat(),
                0f,
                child.width.toFloat(),
                child.height.toFloat(),
                0f,
                child.height.toFloat()
            )
            if (child.isCircle) {
                child.customShadow.drawShadow(canvas, child, roundSoftShadow)
                if (Math.abs(child.rotationX) > 1 || Math.abs(child.rotationY) > 1)
                    drawCornerBaseShape(child, canvas, src)
            } else {
                child.customShadow.drawShadow(canvas, child, softShadow)
                if (child.rotationX != 0f || child.rotationY != 0f) {
                    if (getLongestHorizontalEdge(child) > getLongestVerticalEdge(child))
                        drawVerticalFirst(child, canvas, src)
                    else
                        drawHorizontalFist(child, canvas, src)
                }
            }
        }
        return super.drawChild(canvas, child, drawingTime)
    }

    private fun drawCornerBaseShape(dl: DepthLayout, canvas: Canvas, src: FloatArray) {
        val dst = floatArrayOf(
            dl.topLeftBack.x,
            dl.topLeftBack.y,
            dl.topRightBack.x,
            dl.topRightBack.y,
            dl.bottomRightBack.x,
            dl.bottomRightBack.y,
            dl.bottomLeftBack.x,
            dl.bottomLeftBack.y
        )
        val count = canvas.save()
        matrixX.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        canvas.concat(matrixX)
        edgePath.reset()
        edgePath.addRoundRect(
            0f,
            0f,
            dl.width.toFloat(),
            dl.height.toFloat(),
            dl.width / 2f,
            dl.height / 2f,
            Path.Direction.CCW
        )

        canvas.drawPath(edgePath, dl.edgePaint)
        shadowPaint.alpha = (this.shadowAlpha * 0.5f * 255f).toInt()
        canvas.drawPath(edgePath, shadowPaint)

        canvas.restoreToCount(count)
    }


    private fun drawHorizontalFist(dl: DepthLayout, canvas: Canvas, src: FloatArray) {
        if (getLeftEdgeLength(dl) <= getRightEdgeLength(dl)) {
            drawLeftEdge(dl, canvas, src)
        } else {
            drawRightEdge(dl, canvas, src)
        }

        drawTopEdge(dl, canvas, src)
        drawBottomEdge(dl, canvas, src)

        if (getLeftEdgeLength(dl) >= getRightEdgeLength(dl)) {
            drawLeftEdge(dl, canvas, src)
        } else {
            drawRightEdge(dl, canvas, src)
        }
    }

    private fun drawVerticalFirst(dl: DepthLayout, canvas: Canvas, src: FloatArray) {

        if (getTopEdgeLength(dl) <= getBottomEdgeLength(dl)) {
            drawTopEdge(dl, canvas, src)
        } else {
            drawBottomEdge(dl, canvas, src)
        }

        drawLeftEdge(dl, canvas, src)
        drawRightEdge(dl, canvas, src)


        if (getTopEdgeLength(dl) >= getBottomEdgeLength(dl)) {
            drawTopEdge(dl, canvas, src)
        } else {
            drawBottomEdge(dl, canvas, src)
        }

    }

    private fun getLongestHorizontalEdge(dl: DepthLayout): Float {
        val topEdgeLength = getTopEdgeLength(dl)
        val bottomEdgeLength = getBottomEdgeLength(dl)
        return if (topEdgeLength > bottomEdgeLength) {
            topEdgeLength
        } else {
            bottomEdgeLength
        }
    }

    private fun getLongestVerticalEdge(dl: DepthLayout): Float {
        val leftEdgeLength = getLeftEdgeLength(dl)
        val rightEdgeLength = getRightEdgeLength(dl)
        return if (leftEdgeLength > rightEdgeLength) {
            leftEdgeLength
        } else {
            rightEdgeLength
        }
    }

    private fun getRightEdgeLength(dl: DepthLayout): Float {
        return getDistance(dl.topRightBack, dl.bottomRightBack)
    }

    private fun getLeftEdgeLength(dl: DepthLayout): Float {
        return getDistance(dl.topLeftBack, dl.bottomLeftBack)
    }


    private fun getBottomEdgeLength(dl: DepthLayout): Float {
        return getDistance(dl.bottomLeftBack, dl.bottomRightBack)
    }


    private fun drawShadow(point1: PointF, point2: PointF, correctionValue: Float, canvas: Canvas, dl: DepthLayout) {
        val angle = Math.abs(Math.abs(getAngle(point1, point2)) + correctionValue)
        val alpha = angle / 180f
        shadowPaint.alpha = (alpha * 255f * this.shadowAlpha).toInt()

        canvas.drawRect(0f, 0f, dl.width.toFloat(), dl.height.toFloat(), shadowPaint)
    }


    private fun drawRectangle(dl: DepthLayout, canvas: Canvas) {
        canvas.drawRect(0f, 0f, dl.width.toFloat(), dl.height.toFloat(), dl.edgePaint)
    }

    private fun getAngle(point1: PointF, point2: PointF): Float {

        return Math.toDegrees(Math.atan2((point1.y - point2.y).toDouble(), (point1.x - point2.x).toDouble())).toFloat()
    }

    private fun drawLeftEdge(dl: DepthLayout, canvas: Canvas, src: FloatArray) {
        val dst = floatArrayOf(
            dl.topLeft.x,
            dl.topLeft.y,
            dl.topLeftBack.x,
            dl.topLeftBack.y,
            dl.bottomLeftBack.x,
            dl.bottomLeftBack.y,
            dl.bottomLeft.x,
            dl.bottomLeft.y
        )
        val count = canvas.save()
        matrixX.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        canvas.concat(matrixX)
        drawRectangle(dl, canvas)
        drawShadow(dl.topLeft, dl.bottomLeft, 0f, canvas, dl)

        canvas.restoreToCount(count)

    }

    private fun drawRightEdge(dl: DepthLayout, canvas: Canvas, src: FloatArray) {
        val dst = floatArrayOf(
            dl.topRight.x,
            dl.topRight.y,
            dl.topRightBack.x,
            dl.topRightBack.y,
            dl.bottomRightBack.x,
            dl.bottomRightBack.y,
            dl.bottomRight.x,
            dl.bottomRight.y
        )
        val count = canvas.save()
        matrixX.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        canvas.concat(matrixX)
        drawRectangle(dl, canvas)
        drawShadow(dl.topRight, dl.bottomRight, -180f, canvas, dl)
        canvas.restoreToCount(count)
    }

    private fun drawTopEdge(dl: DepthLayout, canvas: Canvas, src: FloatArray) {

        val dst = floatArrayOf(
            dl.topLeft.x,
            dl.topLeft.y,
            dl.topRight.x,
            dl.topRight.y,
            dl.topRightBack.x,
            dl.topRightBack.y,
            dl.topLeftBack.x,
            dl.topLeftBack.y
        )
        val count = canvas.save()
        matrixX.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        canvas.concat(matrixX)
        drawRectangle(dl, canvas)
        drawShadow(dl.topLeft, dl.topRight, -180f, canvas, dl)
        canvas.restoreToCount(count)
    }

    private fun drawBottomEdge(dl: DepthLayout, canvas: Canvas, src: FloatArray) {

        val dst = floatArrayOf(
            dl.bottomLeft.x,
            dl.bottomLeft.y,
            dl.bottomRight.x,
            dl.bottomRight.y,
            dl.bottomRightBack.x,
            dl.bottomRightBack.y,
            dl.bottomLeftBack.x,
            dl.bottomLeftBack.y
        )
        val count = canvas.save()
        matrixX.setPolyToPoly(src, 0, dst, 0, dst.size shr 1)
        canvas.concat(matrixX)
        drawRectangle(dl, canvas)
        drawShadow(dl.bottomLeft, dl.bottomRight, 0f, canvas, dl)
        canvas.restoreToCount(count)
    }
}
