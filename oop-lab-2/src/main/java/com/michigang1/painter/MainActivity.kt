package com.michigang1.painter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    private lateinit var drawingShapeView: DrawingShapeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawingShapeView = DrawingShapeView(this)
        setContentView(drawingShapeView)
        showSystemUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_line -> {
                drawingShapeView.mCurrentShape = DrawingShapeView.LINE
                val titleLine = resources.getString(R.string.action_line)
                setTitle(titleLine)
            }
            R.id.action_smoothline -> {
                drawingShapeView.mCurrentShape = DrawingShapeView.SMOOTHLINE
                val titleSmoothLine = resources.getString(R.string.action_smoothline)
                setTitle(titleSmoothLine)
            }
            R.id.action_rectangle -> {
                drawingShapeView.mCurrentShape = DrawingShapeView.RECTANGLE
                val titleRectangle = resources.getString(R.string.action_rectangle)
                setTitle(titleRectangle)
            }
            R.id.action_square -> {
                drawingShapeView.mCurrentShape = DrawingShapeView.SQUARE
                val titleSquare = resources.getString(R.string.action_square)
                setTitle(titleSquare)
            }
            R.id.action_ellipse -> {
                drawingShapeView.mCurrentShape = DrawingShapeView.ELLIPSE
                val titleEllipse = resources.getString(R.string.action_ellipse)
                setTitle(titleEllipse)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, drawingShapeView).show(WindowInsetsCompat.Type.systemBars())
    }
    private fun setTitle(title: String) {
        supportActionBar?.subtitle = "Selected object: $title"
    }
}
