package com.michigang1.painter

import android.annotation.SuppressLint
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
class DrawingShapeView(context: Context?) : View(context) {
    var mCurrentShape = 0

    // Configuration
    private lateinit var mPath: Path
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas
    private var mTouchTolerance: Int = 0

    // Figures
    private val shapeLine = ShapeLine(Color.RED)
    private val shapeSmoothLine = ShapeSmoothLine(Color.RED)
    private val shapeRectangle = ShapeRectangle(Color.BLACK, Color.RED)
    private val shapeSquare = ShapeSquare(Color.BLACK, Color.WHITE)
    private val shapeEllipse = ShapeEllipse(Color.BLACK, Color.BLACK, Paint.Style.STROKE)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap, 0f, 0f, null)
        if (isDrawing) {
            when (mCurrentShape) {
                LINE -> shapeLine.onDrawShape(canvas)
                SMOOTHLINE -> shapeSmoothLine.onDrawShape(canvas)
                RECTANGLE -> shapeRectangle.onDrawShape(canvas)
                SQUARE -> shapeSquare.onDrawShape(canvas)
                ELLIPSE -> shapeEllipse.onDrawShape(canvas)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mCurrentX = event!!.x
        mCurrentY = event!!.y
        when (mCurrentShape) {
            LINE -> shapeLine.onTouchEventShape(event)
            SMOOTHLINE -> shapeSmoothLine.onTouchEventShape(event)
            RECTANGLE -> shapeRectangle.onTouchEventShape(event)
            SQUARE -> shapeSquare.onTouchEventShape(event)
            ELLIPSE -> shapeEllipse.onTouchEventShape(event)
        }
        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        mPath = Path()
        mTouchTolerance = ViewConfiguration.get(context).scaledTouchSlop
    }

    // ------------------------------------------------------------------
    // Line
    // ------------------------------------------------------------------

    inner class ShapeLine(color: Int) : Shape() {
        private val paint = Paint().setPaintShape(color)
        private val paintFinal = Paint().setPaintShape(color)
        override fun onDrawShape(canvas: Canvas?) {
            val dx = abs(mCurrentX - mStartX)
            val dy = abs(mCurrentY - mStartY)
            if (dx >= mTouchTolerance || dy >= mTouchTolerance) {
                canvas?.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, paintFinal)
                canvas?.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, paint)
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
                    mCanvas.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, paintFinal)
                    mCanvas.drawLine(mStartX, mStartY, mCurrentX, mCurrentY, paint)
                    invalidate()
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Smooth Line
    // ------------------------------------------------------------------
    inner class ShapeSmoothLine(color: Int) : Shape() {
        private val paint = Paint().setPaintShape(color)
        override fun onDrawShape(canvas: Canvas?) {
            // empty  body
        }

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
                    if (dX >= mTouchTolerance || dY >= mTouchTolerance) {
                        val lastX = (mCurrentX + mStartX) / 2
                        val lastY = (mCurrentY + mStartY) / 2
                        mPath.quadTo(mStartX, mStartY, lastX, lastY)
                        mStartX = mCurrentX
                        mStartY = mCurrentY
                    }
                    mCanvas.drawPath(mPath, paint)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    isDrawing = false
                    mPath.lineTo(mStartX, mStartY)
                    mCanvas.drawPath(mPath, paint)
                    mPath.reset()
                    invalidate()
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Ellipse
    // ------------------------------------------------------------------
    inner class ShapeEllipse(
        colorStroke: Int,
        colorFill: Int,
        styleFill: Paint.Style = Paint.Style.FILL
    ) : Shape() {
        private val paint = Paint().setPaintShape(colorStroke)
        private val paintFinal = Paint().setPaintShape(colorFill, styleFill)
        override fun onDrawShape(canvas: Canvas?) {
            drawEllipse(canvas, paintFinal)
            drawEllipse(canvas, paint)
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
                    drawEllipse(mCanvas, paintFinal)
                    drawEllipse(mCanvas, paint)
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
    inner class ShapeRectangle(
        colorStroke: Int,
        colorFill: Int,
        styleFill: Paint.Style = Paint.Style.FILL_AND_STROKE
    ) : Shape() {
        private val paint = Paint().setPaintShape(colorStroke)
        private val paintFinal = Paint().setPaintShape(colorFill, styleFill)

        override fun onDrawShape(canvas: Canvas?) {
            drawRectangular(canvas, paintFinal)
            drawRectangular(canvas, paint)
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
                    drawRectangular(mCanvas, paintFinal)
                    drawRectangular(mCanvas, paint)
                    invalidate()
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Square
    // ------------------------------------------------------------------

    inner class ShapeSquare(
        colorStroke: Int,
        colorFill: Int,
        styleFill: Paint.Style = Paint.Style.FILL
    ) : Shape() {
        private val paint = Paint().setPaintShape(colorStroke)
        private val paintFinal = Paint().setPaintShape(colorFill, styleFill)

        override fun onDrawShape(canvas: Canvas?) {
            drawRectangular(canvas, paintFinal)
            drawRectangular(canvas, paint)
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
                    drawRectangular(mCanvas, paintFinal)
                    drawRectangular(mCanvas, paint)
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
    fun drawRectangular(canvas: Canvas?, paint: Paint?) {
        val right = if (mStartX > mCurrentX) mStartX else mCurrentX
        val left = if (mStartX > mCurrentX) mCurrentX else mStartX
        val bottom = if (mStartY > mCurrentY) mStartY else mCurrentY
        val top = if (mStartY > mCurrentY) mCurrentY else mStartY
        canvas!!.drawRect(left, top, right, bottom, paint!!)
    }

    companion object {
        // Modes
        const val LINE = 1
        const val SMOOTHLINE = 2
        const val RECTANGLE = 3
        const val SQUARE = 4
        const val ELLIPSE = 5

        const val TOUCH_STROKE_WIDTH = 5f

        // Coordinates of touch
        private var mStartX = 0f
        private var mStartY = 0f
        private var mCurrentX = 0f
        private var mCurrentY = 0f

        // Flag
        private var isDrawing = false
    }
}
