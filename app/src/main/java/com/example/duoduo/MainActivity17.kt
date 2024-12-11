package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity17 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main17)
        val voltar = findViewById<ImageButton>(R.id.imageButton20)

        voltar.setOnClickListener {
            val intent = Intent(this, MainActivity15::class.java)
            startActivity(intent)

        }
        val enviar = findViewById<Button>(R.id.button45)

        enviar.setOnClickListener {
            val intent = Intent(this, MainActivity19::class.java)
            startActivity(intent)
        }
        val editar = findViewById<Button>(R.id.button46)

        editar.setOnClickListener {
            val intent = Intent(this, MainActivity19::class.java)
            startActivity(intent)
        }
        val mudar = findViewById<Button>(R.id.button47)

        mudar.setOnClickListener {
            val intent = Intent(this, MainActivity19::class.java)
            startActivity(intent)
        }

    }
}