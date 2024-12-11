package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main9)
        val voltar = findViewById<ImageButton>(R.id.imageButton10)

        voltar.setOnClickListener {
            val intent = Intent(this, TelaDeModulos::class.java)
            startActivity(intent)

        }
        val enviar = findViewById<Button>(R.id.button26)

        enviar.setOnClickListener {
            val intent = Intent(this, TelaPosPerguntas::class.java)
            startActivity(intent)
        }
    }
}