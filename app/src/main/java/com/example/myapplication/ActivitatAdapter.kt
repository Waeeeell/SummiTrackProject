package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ActivitatAdapter(private var lista: List<Activitat>) :
    RecyclerView.Adapter<ActivitatAdapter.ActivitatViewHolder>() {

    class ActivitatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivitatViewHolder(layoutInflater.inflate(R.layout.item_activitat, parent, false))
    }

    override fun onBindViewHolder(holder: ActivitatViewHolder, position: Int) {
        val item = lista[position]
        holder.tvTitulo.text = item.NombreRuta

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Actividad: ${item.NombreRuta}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = lista.size

    fun updateList(nuevaLista: List<Activitat>) {
        this.lista = nuevaLista
        notifyDataSetChanged()
    }
}