package me.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

@SuppressLint("ViewConstructor")
class PointShape(
    context: Context,
    canvas: Canvas,
    bitmap: Bitmap,
    strokeColor: Int,
    fillColor: Int
) : Shape(context, canvas, bitmap, strokeColor, fillColor) {
    override fun drawShape(canvas: Canvas, paint: Paint) {
        canvas.drawPoint(mStartX, mStartY, paint)
    }
}
