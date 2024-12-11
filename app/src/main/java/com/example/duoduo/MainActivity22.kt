package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity22 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main22)
        val voltar = findViewById<ImageButton>(R.id.imageButton25)

        voltar.setOnClickListener {
            val intent = Intent(this, MainActivity21::class.java)
            startActivity(intent)

        }
    }
}