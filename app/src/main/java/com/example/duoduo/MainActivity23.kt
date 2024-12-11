package com.example.duoduo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity23 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main23)
        val voltar = findViewById<ImageButton>(R.id.imageButton26)

        voltar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)

        }
    }
}