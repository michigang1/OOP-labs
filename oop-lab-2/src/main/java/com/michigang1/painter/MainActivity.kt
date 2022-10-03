package com.michigang1.painter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {
    private lateinit var drawingView: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawingView = DrawingView(this)
        setContentView(drawingView)
        showSystemUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_line -> {
                drawingView.mCurrentShape = DrawingView.LINE
            }
            R.id.action_smoothline -> {
                drawingView.mCurrentShape = DrawingView.SMOOTHLINE
            }
            R.id.action_rectangle -> {
                drawingView.mCurrentShape = DrawingView.RECTANGLE
            }
            R.id.action_square -> {
                drawingView.mCurrentShape = DrawingView.SQUARE
            }
            R.id.action_ellipse -> {
                drawingView.mCurrentShape = DrawingView.ELLIPSE
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, drawingView).show(WindowInsetsCompat.Type.systemBars())
    }
}
