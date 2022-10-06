package com.michigang1.painter

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class DrawingShapeView(context: Context?) : View(context) {

    // Configuration
    private lateinit var mBitmap: Bitmap
    private lateinit var mCanvas: Canvas

    init {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    // Shapes
    val rectangleShape = RectangleShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.WHITE)
    val squareShape = SquareShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.WHITE)
    val ellipseShape = EllipseShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.TRANSPARENT)
    val lineShape = LineShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.TRANSPARENT)
    val smoothLineShape = SmoothLineShape(context!!, mCanvas, mBitmap, Color.YELLOW, Color.TRANSPARENT)

    // Strings of shapes
    val titleLine = resources.getString(R.string.action_line)
    val titleSmoothLine = resources.getString(R.string.action_smoothline)
    val titleRectangle = resources.getString(R.string.action_rectangle)
    val titleSquare = resources.getString(R.string.action_square)
    val titleEllipse = resources.getString(R.string.action_ellipse)

    fun AppCompatActivity.toSelectShape(title: String, shape: Shape) {
        val subtitle = "Selected object: $title"
        supportActionBar?.subtitle = subtitle
        setContentView(shape)
    }
}
