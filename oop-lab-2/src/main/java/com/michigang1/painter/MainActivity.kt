package com.michigang1.painter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var drawingView: DrawingShapeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawingView = DrawingShapeView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        drawingView.apply {
            when (item.itemId) {
                R.id.action_line -> toSelectShape(titleLine, lineShape)

                R.id.action_smoothline -> toSelectShape(titleSmoothLine, smoothLineShape)

                R.id.action_rectangle -> toSelectShape(titleRectangle, rectangleShape)

                R.id.action_square -> toSelectShape(titleSquare, squareShape)

                R.id.action_ellipse -> toSelectShape(titleEllipse, ellipseShape)
            }
            return super.onOptionsItemSelected(item)
        }
    }
}
