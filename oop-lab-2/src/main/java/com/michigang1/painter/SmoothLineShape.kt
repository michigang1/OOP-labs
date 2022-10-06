package com.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class SmoothLineShape(
    context: Context,
    canvas: Canvas,
    bitmap: Bitmap,
    strokeColor: Int,
    private val fillColor: Int = Color.TRANSPARENT

) : Shape(context, canvas, bitmap, strokeColor, fillColor) {
    override fun drawShape(canvas: Canvas, paint: Paint) {
        val dX = abs(mCurrentX - mStartX)
        val dY = abs(mCurrentY - mStartY)
        if (dX >= TOUCH_TOLERANCE || dY >= TOUCH_TOLERANCE) {
            val lastX = (mCurrentX + mStartX) / 2
            val lastY = (mCurrentY + mStartY) / 2
            path.quadTo(mStartX, mStartY, lastX, lastY)
            mStartX = mCurrentX
            mStartY = mCurrentY
        }
        canvas.drawPath(path, paint)
    }
}
