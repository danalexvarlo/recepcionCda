package com.example.recepcioncda.view.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.activities.LoginActivity
import com.example.recepcioncda.view.ui.models.Formulario
import com.example.recepcioncda.view.ui.models.Usuario
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

private lateinit var ejeUnoDerecha: EditText
private lateinit var ejeUnoIzquierda: EditText
private lateinit var ejeDosDerecha: EditText
private lateinit var ejeDosIzquierda: EditText
private lateinit var repuestoLiviano: EditText
class LivianoFragment : Fragment() {

    lateinit var toggle : ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_liviano, container, false)
        val navView: NavigationView = view.findViewById(R.id.nav_view)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_liviano)
        val drawerLayout : DrawerLayout = view.findViewById(R.id.livianoFragment)
        nombreRecepcionista = view.findViewById(R.id.nombreRecepcionistaLiviano)
        nombreRecepcionista.text = Usuario.nombre?: "Usuario desconocido"

        ejeUnoDerecha = view.findViewById(R.id.delanteraDerechaEdit)
        ejeUnoDerecha.transformationMethod = null
        ejeUnoIzquierda = view.findViewById(R.id.delanteraIzquierdaEdit)
        ejeUnoIzquierda.transformationMethod = null
        ejeDosDerecha = view.findViewById(R.id.traseraDerechaEdit)
        ejeDosDerecha.transformationMethod = null
        ejeDosIzquierda = view.findViewById(R.id.traseraIzquierdaEdit)
        ejeDosIzquierda.transformationMethod = null
        repuestoLiviano = view.findViewById(R.id.repuestoEdit)
        repuestoLiviano.transformationMethod = null

        toggle = ActionBarDrawerToggle(this.requireContext() as AppCompatActivity,
            drawerLayout, toolbar, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationOnClickListener{
            if(drawerLayout.isDrawerOpen((GravityCompat.START))){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        navView.setNavigationItemSelectedListener() {
            when (it.itemId) {
                R.id.homeBar -> { //Item para regresar al Home
                    findNavController().navigate(R.id.homeFragment)
                    Toast.makeText(context, "Menú principal", Toast.LENGTH_SHORT).show()
                }

                R.id.logout -> {
                    cerrarSesion()
                    Toast.makeText(context, "Sesión finalizada", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonSiguiente = view.findViewById<Button>(R.id.siguientePresionesLivianoButton)
        buttonSiguiente.setOnClickListener{
            val ejeUnoDerechaValue = ejeUnoDerecha.text.toString().trim()
            val ejeUnoIzquierdaValue = ejeUnoIzquierda.text.toString().trim()
            val ejeDosDerechaValue = ejeDosDerecha.text.toString().trim()
            val ejeDosIzquierdaValue = ejeDosIzquierda.text.toString().trim()
            val presionRepuestoValue = repuestoLiviano.text.toString().trim()
            if(ejeUnoDerechaValue.isNotEmpty() && ejeUnoIzquierdaValue.isNotEmpty()
                && ejeDosDerechaValue.isNotEmpty() && ejeDosIzquierdaValue.isNotEmpty()
                && presionRepuestoValue.isNotEmpty()) {
                Formulario.presiondDe = ejeUnoDerecha.text.toString().toIntOrNull()
                Formulario.presiondIz = ejeUnoIzquierda.text.toString().toIntOrNull()
                Formulario.presiontIz = ejeDosIzquierda.text.toString().toIntOrNull()
                Formulario.presiontDe = ejeDosDerecha.text.toString().toIntOrNull()
                Formulario.presionRep = repuestoLiviano.text.toString().toIntOrNull()
                findNavController().navigate(R.id.action_livianoFragment_to_observacionesFragment)
            }
            else{ Toast.makeText(requireContext(), "Revise espacios en blanco", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun cerrarSesion() { //Función para cerrar sesión y volver al login
        Usuario.nombre = null  // Se limpia la variable global del usuario
        //findNavController().navigate(R.id.loginActivity)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

}