package com.example.duoduo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaDeCadastro : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var fb: FirebaseFirestore
        fb = FirebaseFirestore.getInstance()

        var fbAuth: FirebaseAuth
        fbAuth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_de_cadastro)

        val cadastroEmail = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val cadastroSenha = findViewById<EditText>(R.id.editTextTextPassword)
        val nome = findViewById<EditText>(R.id.editTextText)

        val botaoVoltarTela2ParaTela1 = findViewById<ImageButton>(R.id.imageButton4)
        val cadastrar = findViewById<Button>(R.id.button3)
        val flagUser = findViewById<Button>(R.id.button70)
        val flagAdm = findViewById<Button>(R.id.button69)
        var isAdm = false
        var isUser = false

        botaoVoltarTela2ParaTela1.setOnClickListener {
            val intent = Intent(this, TelaDeLogin::class.java)
            startActivity(intent)
        }

        flagUser.setOnClickListener {
            isUser = true
            isAdm = false // Garante que não seja marcado como administrador ao clicar em usuário
            Toast.makeText(this, "Modo usuário selecionado.", Toast.LENGTH_SHORT).show()
        }

        flagAdm.setOnClickListener {
            isAdm = true
            isUser = false // Garante que não seja marcado como usuário ao clicar em administrador
            Toast.makeText(this, "Modo administrador selecionado.", Toast.LENGTH_SHORT).show()
        }

        cadastrar.setOnClickListener {
            val emailTexto = cadastroEmail.text.toString().trim()
            val senhaTexto = cadastroSenha.text.toString().trim()
            val nomeTexto = nome.text.toString().trim()

            if (nomeTexto.isNotEmpty() && emailTexto.isNotEmpty() && senhaTexto.isNotEmpty()) {
                if (!isAdm && !isUser) { // Certifica-se de que uma opção foi selecionada
                    Toast.makeText(this, "Por favor, selecione se o usuário é administrador ou comum.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                fbAuth.createUserWithEmailAndPassword(emailTexto, senhaTexto)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            val userId = fbAuth.currentUser?.uid
                            val userMap = mapOf(
                                "uid" to userId,
                                "nome" to nomeTexto,
                                "email" to emailTexto,
                                "isAdm" to isAdm
                            )

                            if (userId != null) {
                                fb.collection("users").document(userId).set(userMap)
                                    .addOnSuccessListener {
                                        startActivity(Intent(this, Menu::class.java))
                                        val role = if (isAdm) "Administrador" else "Usuário Comum"
                                        Toast.makeText(this, "$role cadastrado com sucesso.", Toast.LENGTH_SHORT).show()
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(this, "Erro ao salvar no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(this, "Erro ao obter UID do usuário.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Erro no cadastro: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }


        Log.d("Cadastro", "Nome: ${nome.text.toString().trim()}")
            Log.d("Cadastro", "Email: ${cadastroEmail.text.toString().trim()}")
            Log.d("Cadastro", "Senha: ${cadastroSenha.text.toString().trim()}")


            /*
                fb.collection("users").add(

                    mapOf(
                        "nome" to nome.text.toString(),
                        "email" to cadastroEmail.text.toString(),
                        "senha" to cadastroSenha.text.toString(),
                        "isAdm" to isAdm
                    )
                ).addOnSuccessListener {
                    if (isAdm == false) {
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT)
                            .show()
                    } else if (isAdm == true){
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT)
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao cadastrar: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT)
                    .show()
            }
            */

        }

    }

        //pra deletar usuarios no banco de dados
        //fb.collection("users").document().delete()

        //dar update no user no banco de dados
        //fb.collection("users").document().update(mapOf("nome" to nome.text.toString(), "email" to cadastroEmail.text.toString(), "senha" to cadastroSenha.text.toString()))

