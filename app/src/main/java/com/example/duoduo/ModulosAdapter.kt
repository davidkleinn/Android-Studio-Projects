package com.example.duoduo

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ModulosAdapter(
    private var modulosList: List<String>,
    private val context: Context // Receber o contexto da Activity
) : RecyclerView.Adapter<ModulosAdapter.ModuloViewHolder>(), android.widget.Filterable {

    private var filteredList: List<String> = modulosList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuloViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ModuloViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModuloViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    inner class ModuloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(android.R.id.text1)

        fun bind(modulo: String) {
            textView.text = modulo
            itemView.setOnClickListener {
                // Navegar para a tela de dificuldades
                val intent = Intent(context, TelaDeDificuldades::class.java)
                intent.putExtra("moduloSelecionado", modulo) // Passa o módulo selecionado
                context.startActivity(intent)
            }
        }
    }

    override fun getFilter(): android.widget.Filter {
        return object : android.widget.Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredList = if (charString.isEmpty()) {
                    modulosList
                } else {
                    modulosList.filter { it.contains(charString, ignoreCase = true) }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<String>
                notifyDataSetChanged()
            }
        }
    }
}
