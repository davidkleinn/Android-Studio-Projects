package com.example.duoduo

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class TelaDeGerenciarPerguntas : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tela_de_gerenciar_perguntas)

        db = FirebaseFirestore.getInstance()

        val spinnerModulo = findViewById<Spinner>(R.id.spinnerModulo)
        val editTextPergunta = findViewById<EditText>(R.id.editTextPergunta)
        val editTextRespostaCorreta = findViewById<EditText>(R.id.editTextRespostaCorreta)
        val editTextRespostaIncorreta = findViewById<EditText>(R.id.editTextRespostaIncorreta)
        val spinnerDificuldade = findViewById<Spinner>(R.id.spinnerDificuldade)
        val buttonAdicionarPergunta = findViewById<Button>(R.id.buttonAdicionarPergunta)
        val buttonRemoverPergunta = findViewById<Button>(R.id.buttonRemoverPergunta)
        val buttonAlterarPergunta = findViewById<Button>(R.id.buttonAlterarPergunta)

        // Configurar spinner de módulos (Java, Python, etc.)
        val modulos = listOf("Java", "Python", "C", "Logica")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, modulos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerModulo.adapter = adapter

        // Configurar spinner de dificuldade
        val dificuldades = listOf("easy", "medium", "hard")
        val dificuldadeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dificuldades)
        dificuldadeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDificuldade.adapter = dificuldadeAdapter

        buttonAdicionarPergunta.setOnClickListener {
            val moduloSelecionado = spinnerModulo.selectedItem.toString()
            val pergunta = editTextPergunta.text.toString()
            val respostaCorreta = editTextRespostaCorreta.text.toString()
            val respostasIncorretas = editTextRespostaIncorreta.text.toString().split(",").map { it.trim() }
            val dificuldade = spinnerDificuldade.selectedItem.toString()

            if (pergunta.isNotEmpty() && respostaCorreta.isNotEmpty() && respostasIncorretas.isNotEmpty()) {
                adicionarPergunta(moduloSelecionado, pergunta, respostaCorreta, respostasIncorretas, dificuldade)
            } else {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonRemoverPergunta.setOnClickListener {
            val moduloSelecionado = spinnerModulo.selectedItem.toString()
            val pergunta = editTextPergunta.text.toString()

            if (pergunta.isNotEmpty()) {
                removerPergunta(moduloSelecionado, pergunta)
            } else {
                Toast.makeText(this, "Digite a pergunta a ser removida!", Toast.LENGTH_SHORT).show()
            }
        }

        buttonAlterarPergunta.setOnClickListener {
            val moduloSelecionado = spinnerModulo.selectedItem.toString()
            val pergunta = editTextPergunta.text.toString()
            val novaRespostaCorreta = editTextRespostaCorreta.text.toString()
            val novasRespostasIncorretas = editTextRespostaIncorreta.text.toString().split(",").map { it.trim() }
            val novaDificuldade = spinnerDificuldade.selectedItem.toString()

            if (pergunta.isNotEmpty() && novaRespostaCorreta.isNotEmpty() && novasRespostasIncorretas.isNotEmpty()) {
                alterarPergunta(moduloSelecionado, pergunta, novaRespostaCorreta, novasRespostasIncorretas, novaDificuldade)
            } else {
                Toast.makeText(this, "Preencha todos os campos para alterar!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun adicionarPergunta(
        modulo: String,
        pergunta: String,
        respostaCorreta: String,
        respostasIncorretas: List<String>,
        dificuldade: String
    ) {
        val questionData = hashMapOf(
            "question" to pergunta,
            "answer" to respostaCorreta,
            "options" to respostasIncorretas + respostaCorreta,
            "difficulty" to dificuldade
        )
        db.collection("modules").document(modulo).collection("questions")
            .add(questionData)
            .addOnSuccessListener {
                Toast.makeText(this, "Pergunta adicionada com sucesso!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao adicionar pergunta.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun removerPergunta(modulo: String, pergunta: String) {
        db.collection("modules").document(modulo).collection("questions")
            .whereEqualTo("question", pergunta)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    db.collection("modules").document(modulo).collection("questions")
                        .document(document.id)
                        .delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "Pergunta removida com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao remover pergunta.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun alterarPergunta(
        modulo: String,
        pergunta: String,
        novaRespostaCorreta: String,
        novasRespostasIncorretas: List<String>,
        novaDificuldade: String
    ) {
        db.collection("modules").document(modulo).collection("questions")
            .whereEqualTo("question", pergunta)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val updatedData = hashMapOf(
                        "answer" to novaRespostaCorreta,
                        "options" to novasRespostasIncorretas + novaRespostaCorreta,
                        "difficulty" to novaDificuldade
                    )
                    db.collection("modules").document(modulo).collection("questions")
                        .document(document.id)
                        .update(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Pergunta atualizada com sucesso!", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao alterar pergunta.", Toast.LENGTH_SHORT).show()
            }
    }
}
