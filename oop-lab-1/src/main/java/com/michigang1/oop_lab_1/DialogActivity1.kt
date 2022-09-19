package com.michigang1.oop_lab_1 // ktlint-disable package-name

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michigang1.oop_lab_1.databinding.ActivityDialog1Binding

class DialogActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityDialog1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialog1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            yesBtn.setOnClickListener {
                val stringSeekBarValue = seekBar.progress.toString()
                val intent = Intent(this@DialogActivity1, MainActivity::class.java)
                intent.putExtra("value", stringSeekBarValue)
                finish()
                startActivity(intent)
            }
            noBtn.setOnClickListener {
                val intent = Intent(this@DialogActivity1, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
        }
    }
}
