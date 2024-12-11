package com.example.duoduo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TelaDeFuncoesDeAdm : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tela_de_funcoes_de_adm)


        val voltar = findViewById<Button>(R.id.voltarMenu)
        val gerenUser = findViewById<Button>(R.id.gerenciarUsers)
        val altPeruntas = findViewById<Button>(R.id.alterarPerguntas)

        voltar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }

        gerenUser.setOnClickListener {
            val intent = Intent(this, TelaDeGerenciarUsuarios::class.java)
            startActivity(intent)
        }

        altPeruntas.setOnClickListener {
            val intent = Intent(this, TelaDeGerenciarPerguntas::class.java)
            startActivity(intent)
        }

    }
}