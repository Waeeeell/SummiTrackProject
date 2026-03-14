package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class fragment_detalle_activity : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_activity, container, false)

        val activitat = arguments?.getSerializable("activitat") as? Activitat

        val tvNombre = view.findViewById<TextView>(R.id.tvNombreDetalle)
        val tvDescripcio = view.findViewById<TextView>(R.id.tvDescripcioDetalle)
        val tvDuracion = view.findViewById<TextView>(R.id.tvDuracionDetalle)
        val tvDistancia = view.findViewById<TextView>(R.id.tvDistanciaDetalle)
        val ivImatge = view.findViewById<ImageView>(R.id.ivImatgeDetalle)
        val btnVolver = view.findViewById<Button>(R.id.btnVolverDetalle)

        activitat?.let {
            tvNombre.text = it.nombreRuta
            tvDescripcio.text = it.descripcio
            tvDuracion.text = "Duración: ${it.dias}d ${it.horas}h ${it.minuts}min"
            tvDistancia.text = "Distancia: ${it.distancia} km"

            if (!it.imatgeUrl.isNullOrEmpty()) {
                Glide.with(this)
                    .load(it.imatgeUrl)
                    .placeholder(R.drawable.logo_correcte_no_fons)
                    .error(R.drawable.logo_correcte_no_fons)
                    .centerCrop()
                    .into(ivImatge)
            }
        }

        btnVolver.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return view
    }
}
