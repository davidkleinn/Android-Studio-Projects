package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class TelaDePerguntas : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var perguntas: MutableList<Pergunta> = mutableListOf()
    private lateinit var perguntaTextView: TextView
    private lateinit var respostaButtons: List<Button>
    private var moduloSelecionado: String? = null
    private var dificuldadeSelecionada: String? = null


    private var perguntasRespondidas = 0
    private val maxPerguntas = 3
    var acertos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tela_de_perguntas)

        //println("Módulo Selecionado: $moduloSelecionado")
        //println("Dificuldade Selecionada: $dificuldadeSelecionada")
        var sp = getSharedPreferences("arquivo", MODE_PRIVATE)
        var editor = sp.edit()
        editor.putInt("valor",5)
        editor.apply()

        //
        var sp2 = getSharedPreferences("arquivo", MODE_PRIVATE)
        var x = sp2.getInt("valor",0)


        val voltar = findViewById<ImageButton>(R.id.imageButton17)

        voltar.setOnClickListener {
            val intent = Intent(this, TelaPosPerguntas::class.java)
            startActivity(intent)
        }

        perguntaTextView = findViewById(R.id.textView6)
        respostaButtons = listOf(
            findViewById(R.id.button36),
            findViewById(R.id.button37),
            findViewById(R.id.button38),
            findViewById(R.id.button34)
        )

        moduloSelecionado = intent.getStringExtra("moduloSelecionado")
        dificuldadeSelecionada = intent.getStringExtra("dificuldadeSelecionada")

        if (moduloSelecionado != null) {
            carregarPerguntas(moduloSelecionado!!, dificuldadeSelecionada!!)
        } else {
            Toast.makeText(this, "Erro: módulo não selecionado.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun carregarPerguntas(modulo: String, dificuldade: String) {
        db.collection("modules").document(modulo).collection("questions")
            .whereEqualTo("difficulty", dificuldade).get()
            .addOnSuccessListener { result ->
                perguntas.clear()
                for (document in result) {

                    val pergunta = document.getString("question") ?: ""
                    val options = document.get("options") as? List<String> ?: listOf()
                    val correctAnswer = document.getString("answer")?.trim() ?: ""
                    perguntas.add(Pergunta(pergunta, options, correctAnswer))
                }
                exibirPerguntaAleatoria()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao carregar perguntas", Toast.LENGTH_SHORT).show()
            }
    }

    private fun exibirPerguntaAleatoria() {

        if (perguntas.isNotEmpty() && perguntasRespondidas < maxPerguntas) {
            val perguntaAtual = perguntas.random()
            perguntaTextView.text = perguntaAtual.texto

            val opcoesEmbaralhadas = perguntaAtual.opcoes.shuffled()
            for (i in respostaButtons.indices) {
                respostaButtons[i].text = opcoesEmbaralhadas[i]
                respostaButtons[i].setOnClickListener {
                    verificarResposta(perguntaAtual, respostaButtons[i].text.toString())
                }
            }
        } else {

            irParaProximaTela()
        }
    }
    /*
        private fun verificarResposta(pergunta: Pergunta, respostaSelecionada: String) {
            if (respostaSelecionada == pergunta.respostaCorreta) {
                Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Errado. Próxima pergunta!", Toast.LENGTH_SHORT).show()
            }

            perguntasRespondidas++
            exibirPerguntaAleatoria()
        }
        */

    private fun verificarResposta(pergunta: Pergunta, respostaSelecionada: String) {
        val correta = pergunta.respostaCorreta.trim()
        val selecionada = respostaSelecionada.trim()

        if (selecionada.equals(correta, ignoreCase = true)) {
            Toast.makeText(this, "Correto!", Toast.LENGTH_SHORT).show()
            acertos++

            // Salva o número de acertos no SharedPreferences
            val sp = getSharedPreferences("arquivo", MODE_PRIVATE)
            val editor = sp.edit()
            editor.putInt("acertos", acertos)
            editor.apply()
        } else {
            Toast.makeText(this, "Errado. Próxima pergunta!", Toast.LENGTH_SHORT).show()
        }

        perguntasRespondidas++
        exibirPerguntaAleatoria()
    }



    private fun irParaProximaTela() {
        val intent = Intent(this, TelaPosPerguntas::class.java)
        startActivity(intent)
        finish()
    }

    data class Pergunta(

        val texto: String,
        val opcoes: List<String>,
        val respostaCorreta: String

    )

}