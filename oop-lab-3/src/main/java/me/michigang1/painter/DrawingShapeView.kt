package me.michigang1.painter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("ResourceType")
class DrawingShapeView(context: Context?) : View(context) {

    // Configuration
    private var mBitmap: Bitmap
    private var mCanvas: Canvas

    init {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val height = Resources.getSystem().displayMetrics.heightPixels
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    // Shapes
    val rectangleShape = RectangleShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.BLUE)
    val squareShape = SquareShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.YELLOW)
    val ellipseShape = EllipseShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.WHITE)
    val lineShape = LineShape(context!!, mCanvas, mBitmap, Color.BLACK, Color.TRANSPARENT)
    val pointShape = PointShape(context!!, mCanvas, mBitmap, Color.GREEN, Color.TRANSPARENT)

    val shapesArray = arrayOf(rectangleShape, squareShape, ellipseShape, lineShape, pointShape)

    @SuppressLint("UseSupportActionBar")
    fun <T : Shape> AppCompatActivity.toSelectShape(shapeId: Int, shape: T) {
        val nameOfShape = resources.getString(shapeId)
        val subtitle = "Selected object: $nameOfShape"
        supportActionBar?.subtitle = subtitle
        setContentView(shape)
    }
}
