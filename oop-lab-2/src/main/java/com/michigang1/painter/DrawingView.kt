package com.michigang1.painter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlin.math.abs

class DrawingView(context: Context?) : View(context) {
    var mCurrentShape = 0

    private lateinit var mPath: Path
    private lateinit var mPaint: Paint
    private lateinit var mPaintFinal: Paint
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas

    private var isDrawing = false

    private var mStartX = 0f
    private var mStartY = 0f
    private var mCurrentX = 0f
    private var mCurrentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    // Figures
    private val line = Shapes().Line()
    private val smoothLine = Shapes().SmoothLine()
    private val rectangle = Shapes().Rectangle()
    private val square = Shapes().Square()
    private val ellipse = Shapes().Ellipse()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)
        if (isDrawing) {
            when (mCurrentShape) {
                LINE -> line.onDrawShape(canvas)
                SMOOTHLINE -> smoothLine.onDrawShape(canvas)
                RECTANGLE -> rectangle.onDrawShape(canvas)
                SQUARE -> square.onDrawShape(canvas)
                ELLIPSE -> ellipse.onDrawShape(canvas)
            }
        }
    }

    init {
        mPath = Path()
        mPaint = Paint(Paint.DITHER_FLAG).apply {
            isAntiAlias = true
            isDither = true
            color = Color.GRAY
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = TOUCH_STROKE_WIDTH
        }

        mPaintFinal = Paint(Paint.DITHER_FLAG).apply {
            isAntiAlias = true
            isDither = true
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = TOUCH_STROKE_WIDTH
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mCurrentX = event!!.x
        mCurrentY = event!!.y
        when (mCurrentShape) {
            LINE -> line.onTouchEventShape(event)
            SMOOTHLINE -> smoothLine.onTouchEventShape(event)
            RECTANGLE -> rectangle.onTouchEventShape(event)
            SQUARE -> square.onTouchEventShape(event)
            ELLIPSE -> ellipse.onTouchEventShape(event)
        }
        return true
    }

    // ------------------------------------------------------------------
    // Shapes class
    // ------------------------------------------------------------------
    open inner class Shapes {
        open fun onDrawShape(canvas: Canvas?) {}
        open fun onTouchEventShape(event: MotionEvent?) {}

        // ------------------------------------------------------------------
        // Line
        // ------------------------------------------------------------------
        open inner class Line : DrawingView.Shapes() {
            override fun onDrawShape(canvas: Canvas?) {
                val dx = abs(mCurrentX - mStartX)
                val dy = abs(mCurrentY - mStartY)
                if (dx >= touchTolerance || dy >= touchTolerance) {
                    canvas?.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, mPaint)
                }
            }

            override fun onTouchEventShape(event: MotionEvent?) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isDrawing = true
                        mStartX = mCurrentX
                        mStartY = mCurrentY
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> invalidate()
                    MotionEvent.ACTION_UP -> {
                        isDrawing = false
                        mCanvas.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, mPaintFinal)
                        invalidate()
                    }
                }
            }
        }

        // ------------------------------------------------------------------
        // Smooth Line
        // ------------------------------------------------------------------
        open inner class SmoothLine :
            DrawingView.Shapes() {
            override fun onTouchEventShape(event: MotionEvent?) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isDrawing = true
                        mStartX = mCurrentX
                        mStartY = mCurrentY
                        mPath.reset()
                        mPath.moveTo(mCurrentX, mCurrentY)
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val dX = abs(mCurrentX - mStartX)
                        val dY = abs(mCurrentY - mStartY)
                        if (dX >= touchTolerance || dY >= touchTolerance) {
                            val lastX = (mCurrentX + mStartX) / 2
                            val lastY = (mCurrentY + mStartY) / 2
                            mPath.quadTo(mStartX, mStartY, lastX, lastY)
                            mStartX = mCurrentX
                            mStartY = mCurrentY
                        }
                        mCanvas.drawPath(mPath, mPaint)
                        invalidate()
                    }
                    MotionEvent.ACTION_UP -> {
                        isDrawing = false
                        mPath.lineTo(mStartX, mStartY)
                        mCanvas.drawPath(mPath, mPaintFinal)
                        mPath.reset()
                        invalidate()
                    }
                }
            }
        }

        // ------------------------------------------------------------------
        // Ellipse
        // ------------------------------------------------------------------
        open inner class Ellipse : DrawingView.Shapes() {
            override fun onDrawShape(canvas: Canvas?) {
                drawEllipse(canvas, mPaint)
            }

            override fun onTouchEventShape(event: MotionEvent?) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isDrawing = true
                        mStartX = mCurrentX
                        mStartY = mCurrentY
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> invalidate()
                    MotionEvent.ACTION_UP -> {
                        isDrawing = false
                        drawEllipse(mCanvas, mPaint)
                        invalidate()
                    }
                }
            }
            private fun drawEllipse(canvas: Canvas?, paint: Paint?) {
                val right = if (mStartX > mCurrentX) mStartX else mCurrentX
                val left = if (mStartX > mCurrentX) mCurrentX else mStartX
                val bottom = if (mStartY > mCurrentY) mStartY else mCurrentY
                val top = if (mStartY > mCurrentY) mCurrentY else mStartY
                canvas!!.drawOval(left, top, right, bottom, paint!!)
            }
        }

        // ------------------------------------------------------------------
        // Rectangle
        // ------------------------------------------------------------------
        open inner class Rectangle() : DrawingView.Shapes() {
            override fun onDrawShape(canvas: Canvas?) {
                drawRectangular(canvas, mPaint)
            }

            override fun onTouchEventShape(event: MotionEvent?) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isDrawing = true
                        mStartX = mCurrentX
                        mStartY = mCurrentY
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> invalidate()
                    MotionEvent.ACTION_UP -> {
                        isDrawing = false
                        drawRectangular(mCanvas, mPaintFinal)
                        invalidate()
                    }
                }
            }
        }

        // ------------------------------------------------------------------
        // Square
        // ------------------------------------------------------------------
        open inner class Square : DrawingView.Shapes() {
            override fun onDrawShape(canvas: Canvas?) {
                drawRectangular(canvas, mPaint)
            }
            override fun onTouchEventShape(event: MotionEvent?) {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isDrawing = true
                        mStartX = mCurrentX
                        mStartY = mCurrentY
                        invalidate()
                    }
                    MotionEvent.ACTION_MOVE -> {
                        adjustSquare(mCurrentX, mCurrentY)
                        invalidate()
                    }
                    MotionEvent.ACTION_UP -> {
                        isDrawing = false
                        adjustSquare(mCurrentX, mCurrentY)
                        drawRectangular(mCanvas, mPaintFinal)
                        invalidate()
                    }
                }
            }
            private fun adjustSquare(x: Float, y: Float) {
                val deltaX = abs(mStartX - x)
                val deltaY = abs(mStartY - y)
                val max = deltaX.coerceAtLeast(deltaY)
                mCurrentX = if (mStartX - x < 0) mStartX + max else mStartX - max
                mCurrentY = if (mStartY - y < 0) mStartY + max else mStartY - max
            }
        }
        private fun drawRectangular(canvas: Canvas?, paint: Paint?) {
            val right = if (mStartX > mCurrentX) mStartX else mCurrentX
            val left = if (mStartX > mCurrentX) mCurrentX else mStartX
            val bottom = if (mStartY > mCurrentY) mStartY else mCurrentY
            val top = if (mStartY > mCurrentY) mCurrentY else mStartY
            canvas!!.drawRect(left, top, right, bottom, paint!!)
        }
    }

    companion object {
        const val LINE = 1
        const val RECTANGLE = 3
        const val SQUARE = 4
        const val ELLIPSE = 5
        const val SMOOTHLINE = 2

        const val TOUCH_STROKE_WIDTH = 6f
    }
}
