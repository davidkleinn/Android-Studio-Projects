package com.example.duoduo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu)

        val fb = FirebaseAuth.getInstance().currentUser
        if (fb == null) {
            Log.d("login", "Usuário não autenticado!")
        } else {
            Log.d("login", "Usuário autenticado: ${fb.uid}")
        }
        if (fb != null) {
            val db = FirebaseFirestore.getInstance()
            val userRef = db.collection("users").document(fb.uid!!)

            userRef.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    val isAdmin = document.getBoolean("isAdmin")
                    if (isAdmin == true){
                    mostrarBotaoAdmin(isAdmin)
                        }
                } else {
                    mostrarBotaoAdmin(false)
                }
            }.addOnFailureListener { e ->

                mostrarBotaoAdmin(false)
            }
        } else {
            mostrarBotaoAdmin(false)
        }


        val recompensas = findViewById<Button>(R.id.button7)

        recompensas.setOnClickListener {
            val intent = Intent(this, MainActivity7::class.java)
            startActivity(intent)
        }




        val pesquisar = findViewById<ImageButton>(R.id.imageButton5)

        pesquisar.setOnClickListener {
            val intent = Intent(this, TelaDePesquisarModulos::class.java)
            startActivity(intent)

        }



        val enviar = findViewById<Button>(R.id.button6)

        enviar.setOnClickListener {
            val intent = Intent(this, TelaDeModulos::class.java)
            startActivity(intent)
        }





        val botaoAlterarPerguntas = findViewById<Button>(R.id.botaoAlterarPerguntas)

        val isAdm = intent.getBooleanExtra("isAdm", false)

        if (isAdm == true) {
            botaoAlterarPerguntas.isEnabled = true
            botaoAlterarPerguntas.setOnClickListener {
                val intent = Intent(this, TelaDeFuncoesDeAdm::class.java)
                startActivity(intent)
            }
        } else {
            botaoAlterarPerguntas.isEnabled = false

        }

    }

    private fun mostrarBotaoAdmin(isAdmin: Boolean) {
        val botaoAlterarPerguntas = findViewById<Button>(R.id.botaoAlterarPerguntas)
        if (isAdmin) {
            botaoAlterarPerguntas.visibility = View.VISIBLE
            botaoAlterarPerguntas.setOnClickListener {
                val intent = Intent(this, Menu::class.java) // trocar a mudança para a tela de gerenciar perguntas
                startActivity(intent)
            }
        } else {
            botaoAlterarPerguntas.visibility = View.GONE
        }
    }


}