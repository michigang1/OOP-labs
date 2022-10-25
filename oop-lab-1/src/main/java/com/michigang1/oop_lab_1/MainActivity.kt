package com.michigang1.oop_lab_1 // ktlint-disable package-name

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michigang1.oop_lab_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            txtView.text = intent.getStringExtra("value")
            firstBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, DialogActivity1::class.java)
                finish()
                startActivity(intent)
            }
            secondBtn.setOnClickListener {
                val intent = Intent(this@MainActivity, DialogActivity2::class.java)
                finish()
                startActivity(intent)
            }
        }
    }
}
