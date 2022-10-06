package com.michigang1.painter

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent

abstract class Shape : Canvas() {
    abstract fun onTouchEventShape(event: MotionEvent?)
    abstract fun onDrawShape(canvas: Canvas?)
    open fun Paint.setPaintShape(mColor: Int?, mStyle: Paint.Style? = Paint.Style.STROKE): Paint {
        val paint = Paint(Paint.DITHER_FLAG).apply {
            isAntiAlias = true
            isDither = true
            color = mColor!!
            style = mStyle!!
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = DrawingShapeView.TOUCH_STROKE_WIDTH
        }
        return paint
    }
}
