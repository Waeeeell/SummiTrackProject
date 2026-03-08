package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ActivitatAdapter(
    private var lista: List<Activitat>,
    private val onEditClick: (Activitat) -> Unit,
    private val onDeleteClick: (Activitat) -> Unit
) : RecyclerView.Adapter<ActivitatAdapter.ActivitatViewHolder>() {

    class ActivitatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
        val tvDescripcio: TextView = view.findViewById(R.id.tvDescripcio)
        val tvDuracio: TextView = view.findViewById(R.id.tvDuracio)
        val tvDistancia: TextView = view.findViewById(R.id.tvDistancia)
        val ivImatge: ImageView = view.findViewById(R.id.ivImatge)
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
        val btnEliminar: ImageButton = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ActivitatViewHolder(layoutInflater.inflate(R.layout.item_activitat, parent, false))
    }

    override fun onBindViewHolder(holder: ActivitatViewHolder, position: Int) {
        val item = lista[position]

        holder.tvTitulo.text = item.nombreRuta
        holder.tvDescripcio.text = item.descripcio
        holder.tvDuracio.text = "${item.dias}d ${item.horas}h ${item.minuts}min"
        holder.tvDistancia.text = "${item.distancia} km"

        // Carregar imatge amb Glide
        if (!item.imatgeUrl.isNullOrEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.imatgeUrl)
                .placeholder(R.drawable.logo_correcte_no_fons)
                .error(R.drawable.logo_correcte_no_fons)
                .centerCrop()
                .into(holder.ivImatge)
        } else {
            holder.ivImatge.setImageResource(R.drawable.logo_correcte_no_fons)
        }

        // Botó Editar
        holder.btnEditar.setOnClickListener {
            onEditClick(item)
        }

        // Botó Eliminar
        holder.btnEliminar.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun updateList(nuevaLista: List<Activitat>) {
        this.lista = nuevaLista
        notifyDataSetChanged()
    }
}