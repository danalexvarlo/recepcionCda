package com.example.recepcioncda.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.models.Formulario

class MotocarroFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var ejeDelantero: EditText
    private lateinit var ejeTraseroDerecho: EditText
    private lateinit var ejeTraseroIzquierdo: EditText
    private lateinit var repuesto: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_motocarro, container, false)
        ejeDelantero = view.findViewById(R.id.ejeDelanteroMotocarroEdit)
        ejeDelantero.transformationMethod = null
        ejeTraseroIzquierdo = view.findViewById(R.id.traseraIzquierdaMotocarroEdit)
        ejeTraseroIzquierdo.transformationMethod = null
        ejeTraseroDerecho = view.findViewById(R.id.traseraDerechaMotocarroEdit)
        ejeTraseroDerecho.transformationMethod = null
        repuesto = view.findViewById(R.id.repuestoMotocarroEdit)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonSiguiente = view.findViewById<Button>(R.id.siguientePresionesMotocarroButton)
        buttonSiguiente.setOnClickListener{
            val presionAdelanteValue = ejeDelantero.text.toString().trim()
            val presionTraseroIzquierdoValue = ejeTraseroIzquierdo.text.toString().trim()
            val presionTraseroDerechoValue = ejeTraseroDerecho.text.toString().trim()
            val presionRepuestoValue = repuesto.text.toString().trim()
            if(presionAdelanteValue.isNotEmpty() && presionTraseroIzquierdoValue.isNotEmpty()
                && presionTraseroIzquierdoValue.isNotEmpty() && presionTraseroDerechoValue.isNotEmpty()
                && presionRepuestoValue.isNotEmpty()) {
                Formulario.presiondIz = ejeDelantero.text.toString().toIntOrNull()
                Formulario.presiontIz = ejeTraseroIzquierdo.text.toString().toIntOrNull()
                Formulario.presiontDe = ejeTraseroDerecho.text.toString().toIntOrNull()
                Formulario.presionRep = repuesto.text.toString().toIntOrNull()
            }
            else{ Toast.makeText(requireContext(), "Revise espacios en blanco", Toast.LENGTH_SHORT).show() }
        }
    }
}