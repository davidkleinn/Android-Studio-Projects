package com.example.duoduo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TelaDePesquisarModulos : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ModulosAdapter
    private var modulosList = mutableListOf<String>() // Lista de módulos disponíveis

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tela_de_pesquisar_modulos)

        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchViewModulos)
        recyclerView = findViewById(R.id.recyclerViewModulos)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialize o adapter com uma lista vazia
        adapter = ModulosAdapter(modulosList, this) // Passa o contexto para o adapter
        recyclerView.adapter = adapter

        // Carregar módulos do Firestore
        carregarModulos()

        // Configurar a barra de pesquisa
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    private fun carregarModulos() {
        val db = FirebaseFirestore.getInstance()

        // Obter os documentos da coleção "modules"
        db.collection("modules")
            .get()
            .addOnSuccessListener { documents ->
                modulosList.clear()
                for (document in documents) {
                    // Adicionar o ID do documento na lista de módulos
                    modulosList.add(document.id)
                }
                adapter.notifyDataSetChanged() // Atualiza a RecyclerView
            }
            .addOnFailureListener {
                // Tratar erros de carregamento
                it.printStackTrace()
            }
    }
}
