package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TelaDeModulos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tela_de_modulos)
        val voltar = findViewById<ImageButton>(R.id.imageButton12)

        voltar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)

        }

        val python = findViewById<Button>(R.id.button27)
        python.setOnClickListener {
            startTelaDeDificuldades("Python")

        }

        val java = findViewById<Button>(R.id.button28)
        java.setOnClickListener {
            startTelaDeDificuldades("Java")

        }

        val cpp = findViewById<Button>(R.id.button25)
        cpp.setOnClickListener {
            startTelaDeDificuldades("C")


        }

    }

    private fun startTelaDeDificuldades(modulo: String) {
        val intent = Intent(this, TelaDeDificuldades::class.java)
        intent.putExtra("moduloSelecionado", modulo)
        startActivity(intent)
    }
}