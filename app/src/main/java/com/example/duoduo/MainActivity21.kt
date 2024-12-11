package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity21 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main21)
        val voltar = findViewById<ImageButton>(R.id.imageButton24)

        voltar.setOnClickListener {
            val intent = Intent(this, TelaDeCadastro::class.java)
            startActivity(intent)

        }
        val chatMod = findViewById<Button>(R.id.button63)

        chatMod.setOnClickListener {
            val intent = Intent(this, MainActivity22::class.java)
            startActivity(intent)
        }
    }
}