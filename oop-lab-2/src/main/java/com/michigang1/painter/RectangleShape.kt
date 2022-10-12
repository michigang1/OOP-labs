package com.michigang1.painter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class RectangleShape(
    context: Context,
    canvas: Canvas,
    bitmap: Bitmap,
    strokeColor: Int,
    fillColor: Int

) : Shape(context, canvas, bitmap, strokeColor, fillColor) {
    override fun drawShape(canvas: Canvas, paint: Paint) {
        val right = if (mStartX > mCurrentX) mStartX else mCurrentX
        val left = if (mStartX >mCurrentX) mCurrentX else mStartX
        val bottom = if (mStartY > mCurrentY) mStartY else mCurrentY
        val top = if (mStartY > mCurrentY) mCurrentY else mStartY
        canvas.drawRect(left, top, right, bottom, paint)
    }
}
