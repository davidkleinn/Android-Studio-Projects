package com.example.duoduo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class TelaDeGerenciarUsuarios : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_de_gerenciar_usuarios)

        firestore = FirebaseFirestore.getInstance()
        fbAuth = FirebaseAuth.getInstance()

        val editTextUserName = findViewById<EditText>(R.id.editTextUserName)
        val deleteUserButton = findViewById<Button>(R.id.button_delete_user)

        deleteUserButton.setOnClickListener {
            val userName = editTextUserName.text.toString().trim()
            if (userName.isNotEmpty()) {
                deleteUserByName(userName)
            } else {
                Toast.makeText(this, "Digite um nome para remover o usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUserByName(userName: String) {
        // Busca o usuário pelo nome no Firestore
        firestore.collection("users")
            .whereEqualTo("nome", userName)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        val userId = document.getString("uid")
                        if (userId != null) {
                            // Remove do Firestore
                            firestore.collection("users").document(document.id).delete()
                                .addOnSuccessListener {
                                    // Remove do Authentication
                                    deleteUserFromAuth(userId)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Erro ao remover usuário do Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "UID não encontrado para o usuário.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Nenhum usuário encontrado com o nome $userName.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao buscar usuário no Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteUserFromAuth(userId: String) {
        // Reautenticação do usuário antes de exclusão
        val user = fbAuth.currentUser
        if (user != null && user.uid == userId) {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Usuário removido do Authentication.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Erro ao remover usuário do Authentication: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Erro ao identificar o usuário para exclusão no Authentication.", Toast.LENGTH_SHORT).show()
        }
    }
}
