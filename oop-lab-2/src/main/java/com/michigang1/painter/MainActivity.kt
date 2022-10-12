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
                R.id.action_line -> toSelectShape(R.string.action_line, lineShape)
                R.id.action_point -> toSelectShape(R.string.action_point, pointShape)
                R.id.action_rectangle -> toSelectShape(R.string.action_rectangle, rectangleShape)
                R.id.action_square -> toSelectShape(R.string.action_square, squareShape)
                R.id.action_ellipse -> toSelectShape(R.string.action_ellipse, ellipseShape)
            }
            return super.onOptionsItemSelected(item)
        }
    }
}
