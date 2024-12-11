package com.example.duoduo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaDeLogin : AppCompatActivity() {

    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_de_login)

        fbAuth = FirebaseAuth.getInstance()

        val cadastrarLogin = findViewById<Button>(R.id.button3) // Botão para ir à tela de cadastro

        cadastrarLogin.setOnClickListener {
            val intent = Intent(this, TelaDeCadastro::class.java)
            startActivity(intent)
        }

        val enviar = findViewById<Button>(R.id.button2)
        val loginEmail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val loginSenha = findViewById<EditText>(R.id.editTextTextPassword2)

        enviar.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val senha = loginSenha.text.toString().trim()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                loginUser(email, senha)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        val esqueciSenha = findViewById<Button>(R.id.buttonLog) // Botão de esqueci minha senha
        esqueciSenha.setOnClickListener {
            val intent = Intent(this, Activity3::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firestore = FirebaseFirestore.getInstance()
                    val userId = fbAuth.currentUser?.uid

                    if (userId != null) {
                        firestore.collection("users").document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val isAdm = document.getBoolean("isAdm") == true
                                    val intent = Intent(this, Menu::class.java)
                                    intent.putExtra("isAdm", isAdm) // Passa o status de administrador
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Usuário não encontrado no Firestore", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Erro ao buscar informações do usuário", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Erro ao obter ID do usuário", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Erro ao fazer login: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
