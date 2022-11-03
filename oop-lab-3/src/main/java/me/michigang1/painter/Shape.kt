package me.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

abstract class Shape(
    context: Context,
    private val canvas: Canvas?,
    private val bitmap: Bitmap?,
    private val strokeColor: Int = Color.BLACK,
    private val fillColor: Int = Color.TRANSPARENT
) : View(context) {
    protected var mStartX = 0f
    protected var mStartY = 0f
    protected var mCurrentX = 0f
    protected var mCurrentY = 0f

    private val paint = Paint().apply {
        isAntiAlias
        isDither
        color = strokeColor
        style = Paint.Style.FILL_AND_STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = TOUCH_STROKE_WIDTH
    }
    private var startColor = Color.BLACK

    protected abstract fun drawShape(canvas: Canvas, paint: Paint)

    private fun setPaintShape(colorPaint: Int, stylePaint: Paint.Style) {
        this.paint.color = colorPaint
        this.paint.style = stylePaint
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mCurrentX = event!!.x
        mCurrentY = event.y
        onTouchEventShape(event)
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(bitmap!!, 0f, 0f, null)
        drawShape(canvas!!, paint)
    }

    private fun onTouchEventShape(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
    }

    private fun touchUp() {
        setPaintShape(fillColor, Paint.Style.FILL)
        drawShape(canvas!!, paint)
        setPaintShape(strokeColor, Paint.Style.STROKE)
        drawShape(canvas, paint)
        mStartX = 0f
        mStartY = 0f
        mCurrentX = 0f
        mCurrentY = 0f
        invalidate()
    }

    private fun touchMove() {
        invalidate()
    }

    private fun touchStart() {
        setPaintShape(startColor, Paint.Style.STROKE)
        mStartX = mCurrentX
        mStartY = mCurrentY
        invalidate()
    }
    companion object {
        @JvmStatic
        protected val TOUCH_TOLERANCE = 8f

        @JvmStatic
        protected val TOUCH_STROKE_WIDTH = 8f
    }
}
