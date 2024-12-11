package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity15 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main15)
        val voltar = findViewById<ImageButton>(R.id.imageButton18)

        voltar.setOnClickListener {
            val intent = Intent(this, TelaDeCadastro::class.java)
            startActivity(intent)

        }
        val chatAdm = findViewById<Button>(R.id.button43)

        chatAdm.setOnClickListener {
            val intent = Intent(this, MainActivity16::class.java)
            startActivity(intent)
        }

        val editarperguntas = findViewById<Button>(R.id.button44)

        editarperguntas.setOnClickListener {
            val intent = Intent(this, MainActivity17::class.java)
            startActivity(intent)
        }
        val gerenciaruser = findViewById<Button>(R.id.button42)

        gerenciaruser.setOnClickListener {
            val intent = Intent(this, MainActivity18::class.java)
            startActivity(intent)
        }
        val tickets = findViewById<Button>(R.id.button41)

        tickets.setOnClickListener {
            val intent = Intent(this, MainActivity20::class.java)
            startActivity(intent)
        }
    }
}