package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Activity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_6)
        val voltar = findViewById<ImageButton>(R.id.imageButton7)

        voltar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)

        }
    }
}