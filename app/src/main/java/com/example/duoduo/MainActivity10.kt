package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity10 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main10)
        val comentarios = findViewById<ImageButton>(R.id.imageButton11  )

        comentarios.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)

        }
    }
}