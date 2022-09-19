package com.michigang1.oop_lab_1 // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michigang1.oop_lab_1.databinding.ActivityDialog3Binding

class DialogActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityDialog3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialog3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            yesBtn.setOnClickListener {
                val intent = Intent(this@DialogActivity3, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
            noBtn.setOnClickListener {
                val intent = Intent(this@DialogActivity3, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
            previousBtn.setOnClickListener {
                val intent = Intent(this@DialogActivity3, DialogActivity2::class.java)
                finish()
                startActivity(intent)
            }
        }
    }
}
