package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TelaDeDificuldades : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_de_dificuldades)

        val moduloSelecionado = intent.getStringExtra("moduloSelecionado") ?: "Java"

        val tituloTextView = findViewById<TextView>(R.id.tituloDificuldade)
        val botaoFacil = findViewById<Button>(R.id.buttonFacil)
        val botaoMedio = findViewById<Button>(R.id.buttonMedio)
        val botaoDificil = findViewById<Button>(R.id.buttonDificil)

        botaoFacil.setOnClickListener {
            abrirTelaDePerguntas(moduloSelecionado, "easy")
        }

        botaoMedio.setOnClickListener {
            abrirTelaDePerguntas(moduloSelecionado, "medium")
        }

        botaoDificil.setOnClickListener {
            abrirTelaDePerguntas(moduloSelecionado, "hard")
        }

        tituloTextView.text = "Módulo: $moduloSelecionado - Escolha a Dificuldade"
    }

    private fun abrirTelaDePerguntas(modulo: String, dificuldade: String) {
        val intent = Intent(this, TelaDePerguntas::class.java)
        intent.putExtra("moduloSelecionado", modulo)
        intent.putExtra("dificuldadeSelecionada", dificuldade)
        startActivity(intent)
    }
}
