package com.michigang1.oop_lab_1 // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michigang1.oop_lab_1.databinding.ActivityDialog2Binding

class DialogActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityDialog2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialog2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            nextBtn.setOnClickListener {
                val intent = Intent(this@DialogActivity2, DialogActivity3::class.java)
                finish()
                startActivity(intent)
            }
            noBtn.setOnClickListener {
                val intent = Intent(this@DialogActivity2, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
    }
}
