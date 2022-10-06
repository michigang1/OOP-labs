package com.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class LineShape(
    context: Context,
    canvas: Canvas,
    bitmap: Bitmap,
    strokeColor: Int,
    private val fillColor: Int = Color.TRANSPARENT

) : Shape(context, canvas, bitmap, strokeColor, fillColor) {
    override fun drawShape(canvas: Canvas, paint: Paint) {
        val dx = abs(mCurrentX - mStartX)
        val dy = abs(mCurrentY - mStartY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, paint)
        }
    }
}
