package me.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.abs

@SuppressLint("ViewConstructor")
class SquareShape(
    context: Context,
    canvas: Canvas,
    bitmap: Bitmap,
    strokeColor: Int,
    fillColor: Int
) : Shape(context, canvas, bitmap, strokeColor, fillColor) {
    override fun drawShape(canvas: Canvas, paint: Paint) {
        val deltaX = abs(mStartX - mCurrentX)
        val deltaY = abs(mStartY - mCurrentY)
        val max = deltaX.coerceAtLeast(deltaY)
        mCurrentX = if (mStartX - mCurrentX < 0) mStartX + max else mStartX - max
        mCurrentY = if (mStartY - mCurrentY < 0) mStartY + max else mStartY - max

        val right = if (mStartX > mCurrentX) mStartX else mCurrentX
        val left = if (mStartX > mCurrentX) mCurrentX else mStartX
        val bottom = if (mStartY > mCurrentY) mStartY else mCurrentY
        val top = if (mStartY > mCurrentY) mCurrentY else mStartY
        canvas.drawRect(left, top, right, bottom, paint)
    }
}
