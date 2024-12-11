package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity16 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main16)
        val voltar = findViewById<ImageButton>(R.id.imageButton19)

        voltar.setOnClickListener {
            val intent = Intent(this, MainActivity15::class.java)
            startActivity(intent)

        }
    }
}