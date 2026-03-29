package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

class fragment_home : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: ActivitatAdapter
    private lateinit var viewModel: ActivitatViewModel

    private lateinit var btnFiltroDistancia: Button
    private lateinit var btnFiltroDuracion: Button
    private lateinit var btnFiltroLimpiar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialitzar ViewModel
        viewModel = ViewModelProvider(requireActivity())[ActivitatViewModel::class.java]

        // Inicialitzar RecyclerView
        rv = view.findViewById(R.id.recyclerViewActividades)
        adapter = ActivitatAdapter(
            emptyList(),
            onEditClick = { activitat ->
                val fragment = fragment_add_activity().apply {
                    arguments = Bundle().apply {
                        putSerializable("activitat", activitat)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentRecyclerLista, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onDeleteClick = { activitat -> mostrarDialogConfirmacioEliminar(activitat) },
            onItemClick = { activitat ->
                val fragment = fragment_detalle_activity().apply {
                    arguments = Bundle().apply {
                        putSerializable("activitat", activitat)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentRecyclerLista, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(requireContext())

        // Inicialitzar botons de filtre
        btnFiltroDistancia = view.findViewById(R.id.btnFiltroDistancia)
        btnFiltroDuracion = view.findViewById(R.id.btnFiltroDuracion)
        btnFiltroLimpiar = view.findViewById(R.id.btnFiltroLimpiar)

        // Observar LiveData
        observarViewModel()

        // Configurar listeners dels filtres
        configurarFiltres()

        // Carregar dades de l'API
        viewModel.carregarActivitats()
    }

    override fun onResume() {
        super.onResume()
        val dataStore = com.example.myapplication.stats.StatsDataStore(requireContext())
        androidx.lifecycle.lifecycleScope.launch {
            dataStore.incrementHomeVisits()
        }
    }

    private fun observarViewModel() {
        viewModel.activitats.observe(viewLifecycleOwner) { llista ->
            adapter.updateList(llista)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            // Podries mostrar/amagar un ProgressBar aquí si en tens un al layout
        }

        viewModel.operacioExitosa.observe(viewLifecycleOwner) { exitosa ->
            if (exitosa) {
                Toast.makeText(requireContext(), "Operació realitzada correctament", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configurarFiltres() {
        btnFiltroDistancia.setOnClickListener {
            // Filtrar activitats amb distància > 10 km
            viewModel.filtrarPerDistancia(10)
        }

        btnFiltroDuracion.setOnClickListener {
            // Filtre local per duració (horas > 3)
            val llistaActual = viewModel.activitats.value ?: emptyList()
            val filtrades = llistaActual.filter { it.horas > 3 }
            adapter.updateList(filtrades)
        }

        btnFiltroLimpiar.setOnClickListener {
            // Tornar a carregar totes les activitats
            viewModel.carregarActivitats()
        }
    }

    // ===== DIÀLEG D'ELIMINACIÓ =====
    private fun mostrarDialogConfirmacioEliminar(activitat: Activitat) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminació")
            .setMessage("Estàs segur que vols eliminar l'activitat \"${activitat.nombreRuta}\"?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                activitat.id?.let { id ->
                    viewModel.eliminarActivitat(id)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    // ===== DIÀLEG D'EDITAR =====
    private fun mostrarDialogEditar(activitat: Activitat) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_activitat, null)

        val etNom = dialogView.findViewById<TextInputEditText>(R.id.etNombreRuta)
        val etDesc = dialogView.findViewById<TextInputEditText>(R.id.etDescripcio)
        val npDias = dialogView.findViewById<NumberPicker>(R.id.npDias)
        val npHoras = dialogView.findViewById<NumberPicker>(R.id.npHoras)
        val npMinuts = dialogView.findViewById<NumberPicker>(R.id.npMinuts)
        val npDistancia = dialogView.findViewById<NumberPicker>(R.id.npDistancia)
        val etImatgeUrl = dialogView.findViewById<TextInputEditText>(R.id.etImatgeUrl)

        // Configurar NumberPickers
        npDias.minValue = 0; npDias.maxValue = 30
        npHoras.minValue = 0; npHoras.maxValue = 23
        npMinuts.minValue = 0; npMinuts.maxValue = 59
        npDistancia.minValue = 0; npDistancia.maxValue = 500

        // Omplir amb dades actuals
        etNom.setText(activitat.nombreRuta)
        etDesc.setText(activitat.descripcio)
        npDias.value = activitat.dias
        npHoras.value = activitat.horas
        npMinuts.value = activitat.minuts
        npDistancia.value = activitat.distancia
        etImatgeUrl.setText(activitat.imatgeUrl ?: "")

        AlertDialog.Builder(requireContext())
            .setTitle("Editar Activitat")
            .setView(dialogView)
            .setPositiveButton("Guardar") { dialog, _ ->
                val nom = etNom.text.toString().trim()
                val desc = etDesc.text.toString().trim()

                if (nom.isEmpty()) {
                    Toast.makeText(requireContext(), "El nom no pot estar buit", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val activitatActualitzada = Activitat(
                    id = activitat.id,
                    nombreRuta = nom,
                    descripcio = desc,
                    dias = npDias.value,
                    horas = npHoras.value,
                    minuts = npMinuts.value,
                    distancia = npDistancia.value,
                    imatgeUrl = etImatgeUrl.text.toString().trim().ifEmpty { null }
                )

                activitat.id?.let { id ->
                    viewModel.actualitzarActivitat(id, activitatActualitzada)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    // ===== DIÀLEG DE CREAR (cridat des de fora si cal) =====
    fun mostrarDialogCrear() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_activitat, null)

        val etNom = dialogView.findViewById<TextInputEditText>(R.id.etNombreRuta)
        val etDesc = dialogView.findViewById<TextInputEditText>(R.id.etDescripcio)
        val npDias = dialogView.findViewById<NumberPicker>(R.id.npDias)
        val npHoras = dialogView.findViewById<NumberPicker>(R.id.npHoras)
        val npMinuts = dialogView.findViewById<NumberPicker>(R.id.npMinuts)
        val npDistancia = dialogView.findViewById<NumberPicker>(R.id.npDistancia)
        val etImatgeUrl = dialogView.findViewById<TextInputEditText>(R.id.etImatgeUrl)

        // Configurar NumberPickers
        npDias.minValue = 0; npDias.maxValue = 30
        npHoras.minValue = 0; npHoras.maxValue = 23
        npMinuts.minValue = 0; npMinuts.maxValue = 59
        npDistancia.minValue = 0; npDistancia.maxValue = 500

        AlertDialog.Builder(requireContext())
            .setTitle("Nova Activitat")
            .setView(dialogView)
            .setPositiveButton("Crear") { dialog, _ ->
                val nom = etNom.text.toString().trim()
                val desc = etDesc.text.toString().trim()

                if (nom.isEmpty()) {
                    Toast.makeText(requireContext(), "El nom no pot estar buit", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val novaActivitat = Activitat(
                    nombreRuta = nom,
                    descripcio = desc,
                    dias = npDias.value,
                    horas = npHoras.value,
                    minuts = npMinuts.value,
                    distancia = npDistancia.value,
                    imatgeUrl = etImatgeUrl.text.toString().trim().ifEmpty { null }
                )

                viewModel.crearActivitat(novaActivitat)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel·lar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
