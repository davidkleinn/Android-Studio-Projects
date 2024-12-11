package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class TelaPosPerguntas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tela_pos_perguntas)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val percentageText = findViewById<TextView>(R.id.percentageText)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)

        // Recupera o número de acertos do SharedPreferences
        val sp = getSharedPreferences("arquivo", MODE_PRIVATE)
        val acertos = sp.getInt("acertos", 0)
        val totalPerguntas = 3

        val percentual = if (totalPerguntas > 0) {
            (acertos * 100) / totalPerguntas
        } else {
            0
        }

        progressBar.progress = percentual
        percentageText.text = "$percentual%"

        val feedback = when {
            percentual == 100 -> "Excelente!"
            percentual >= 80 -> "Muito bom!"
            percentual >= 50 -> "Boa tentativa!"
            else -> "Precisa melhorar..."
        }
        feedbackText.text = feedback

        val voltar = findViewById<ImageButton>(R.id.imageButton14)
        voltar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        val enviar = findViewById<Button>(R.id.button24)
        enviar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }

}