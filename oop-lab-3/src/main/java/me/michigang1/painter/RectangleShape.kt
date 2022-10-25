package me.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

@SuppressLint("ViewConstructor")
class RectangleShape(
    context: Context,
    canvas: Canvas,
    bitmap: Bitmap,
    strokeColor: Int,
    fillColor: Int
) : Shape(context, canvas, bitmap, strokeColor, fillColor) {
    override fun drawShape(canvas: Canvas, paint: Paint) {
        val mTempX = 2 * mStartX - mCurrentX
        val mTempY = 2 * mStartY - mCurrentY

        val right = if (mTempX > mCurrentX) mTempX else mCurrentX
        val left = if (mTempX > mCurrentX) mCurrentX else mTempX
        val bottom = if (mTempY > mCurrentY) mCurrentY else mTempY
        val top = if (mTempY > mCurrentY) mTempY else mCurrentY
        canvas.drawRect(left, top, right, bottom, paint)
    }
}
