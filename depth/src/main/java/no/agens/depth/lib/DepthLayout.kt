package no.agens.depth.lib

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.RelativeLayout

class DepthLayout : RelativeLayout {
    var edgePaint = Paint()


    private var depth: Float = 0.toFloat()

    var isCircle = false

    private var prevSrc = FloatArray(8)

    val topLeft = PointF(0f, 0f)
    var topRight = PointF(0f, 0f)
        internal set
    var bottomLeft = PointF(0f, 0f)
        internal set
    var bottomRight = PointF(0f, 0f)
        internal set


    var topLeftBack = PointF(0f, 0f)
        internal set
    var topRightBack = PointF(0f, 0f)
        internal set
    var bottomLeftBack = PointF(0f, 0f)
        internal set
    var bottomRightBack = PointF(0f, 0f)
        internal set


    val customShadow = CustomShadow()

    //        ((View) getParent()).invalidate();
    var customShadowElevation: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        initView(null)

    }

    override fun hasOverlappingRendering(): Boolean {
        return false
    }

    private fun initView(attrs: AttributeSet?) {

        edgePaint.color = DEFAULT_EDGE_COLOR
        edgePaint.isAntiAlias = true
        val arr = context.obtainStyledAttributes(attrs, R.styleable.DepthView)
        if (attrs != null) {
            try {
                edgePaint.color = arr.getInt(R.styleable.DepthView_edge_color, DEFAULT_EDGE_COLOR)
                isCircle = arr.getBoolean(R.styleable.DepthView_is_circle, false)
                depth = arr.getDimension(
                    R.styleable.DepthView_depth,
                    DEFAULT_THICKNESS * resources.displayMetrics.density
                )
                customShadowElevation = arr.getDimension(R.styleable.DepthView_custom_elevation, 0f)
            } finally {
                arr.recycle()
            }

        } else {
            edgePaint.color = DEFAULT_EDGE_COLOR
            depth = DEFAULT_THICKNESS * resources.displayMetrics.density
        }
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {

            }
        }
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(attrs)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }


    fun getDepth(): Float {
        return depth
    }

    fun setDepth(depth: Float) {
        this.depth = depth
        (parent as View).invalidate()
    }

    fun calculateBounds(): Boolean {

        var src = FloatArray(8)
        var dst = floatArrayOf(0f, 0f, width.toFloat(), 0f, 0f, height.toFloat(), width.toFloat(), height.toFloat())
        val matrix = matrix

        matrix.mapPoints(src, dst)
        topLeft.x = src[0] + left
        topLeft.y = src[1] + top
        topRight.x = src[2] + left
        topRight.y = src[3] + top

        bottomLeft.x = src[4] + left
        bottomLeft.y = src[5] + top
        bottomRight.x = src[6] + left
        bottomRight.y = src[7] + top
        val returnValue = hasMatrixChanged(src)
        prevSrc = src
        val percentFrom90X = rotationX / 90f
        val percentFrom90Y = -rotationY / 90f


        matrix.postTranslate(percentFrom90Y * getDepth(), percentFrom90X * getDepth())
        src = FloatArray(8)
        dst = floatArrayOf(0f, 0f, width.toFloat(), 0f, 0f, height.toFloat(), width.toFloat(), height.toFloat())
        matrix.mapPoints(src, dst)

        topLeftBack.x = src[0] + left
        topLeftBack.y = src[1] + top
        topRightBack.x = src[2] + left
        topRightBack.y = src[3] + top

        bottomLeftBack.x = src[4] + left
        bottomLeftBack.y = src[5] + top
        bottomRightBack.x = src[6] + left
        bottomRightBack.y = src[7] + top
        customShadow.calculateBounds(this)

        return returnValue
    }

    private fun hasMatrixChanged(newSrc: FloatArray): Boolean {
        for (i in 0..7) {
            if (newSrc[i] != prevSrc[i])
                return true
        }
        return false
    }

    inner class CustomShadow {
        private var topLeftBack = PointF(0f, 0f)
        private var topRightBack = PointF(0f, 0f)
        private var bottomLeftBack = PointF(0f, 0f)
        private var bottomRightBack = PointF(0f, 0f)
        var padding: Int = 0

        private var matrix = Matrix()

        fun calculateBounds(target: DepthLayout) {
            val src = FloatArray(8)
            val density = resources.displayMetrics.density
            val offsetY = customShadowElevation
            val offsetX = customShadowElevation / 5
            padding = (customShadowElevation / 4f + DEFAULT_SHADOW_PADDING * density).toInt()

            val dst = floatArrayOf(
                (-padding).toFloat(),
                (-padding).toFloat(),
                (target.width + padding).toFloat(),
                (-padding).toFloat(),
                (-padding).toFloat(),
                (target.height + padding).toFloat(),
                (target.width + padding).toFloat(),
                (target.height + padding).toFloat()
            )
            val matrix = getMatrix()
            matrix.mapPoints(src, dst)

            topLeftBack.x = src[0] + target.left.toFloat() + offsetX
            topLeftBack.y = src[1] + target.top.toFloat() + offsetY
            topRightBack.x = src[2] + target.left.toFloat() + offsetX
            topRightBack.y = src[3] + target.top.toFloat() + offsetY

            bottomLeftBack.x = src[4] + target.left.toFloat() + offsetX
            bottomLeftBack.y = src[5] + target.top.toFloat() + offsetY
            bottomRightBack.x = src[6] + target.left.toFloat() + offsetX
            bottomRightBack.y = src[7] + target.top.toFloat() + offsetY

        }

        fun drawShadow(canvas: Canvas, dl: DepthLayout, shadow: Drawable) {

            shadow.setBounds(-padding, -padding, dl.width + padding, dl.height + padding)
            val src = floatArrayOf(
                0f,
                0f,
                dl.width.toFloat(),
                0f,
                dl.width.toFloat(),
                dl.height.toFloat(),
                0f,
                dl.height.toFloat()
            )
            val dst = floatArrayOf(
                topLeftBack.x,
                topLeftBack.y,
                topRightBack.x,
                topRightBack.y,
                bottomRightBack.x,
                bottomRightBack.y,
                bottomLeftBack.x,
                bottomLeftBack.y
            )
            val count = canvas.save()
            matrix.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
            canvas.concat(matrix)
            shadow.draw(canvas)
            canvas.restoreToCount(count)
        }
    }

    companion object {

        const val DEFAULT_SHADOW_PADDING = 10f
        const val PROPERTY_CUSTOM_SHADOW_ELEVATION = "CustomShadowElevation"
        const val DEFAULT_EDGE_COLOR = Color.WHITE
        const val DEFAULT_THICKNESS = 2
    }
}
