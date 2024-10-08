package com.example.recepcioncda.view.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.activities.LoginActivity
import com.example.recepcioncda.view.ui.models.Formulario
import com.example.recepcioncda.view.ui.models.Usuario
import com.google.android.material.navigation.NavigationView
private lateinit var observaciones: EditText
private lateinit var siguienteButton: Button

class ObservacionesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_observaciones, container, false)
        // ------ Se crea la variable del nombre del recepcionista y se le asigna un valor
        nombreRecepcionista = view.findViewById(R.id.nombreRecepcionistaObservaciones)
        nombreRecepcionista.text = Usuario.nombre?: "Usuario desconocido"
        // ------ Se implementa el EditText y Button del fragmento ------ //
        observaciones = view.findViewById(R.id.editObservaciones)
        siguienteButton = view.findViewById(R.id.observacionesSiguienteButton)
        //------ Se implementa el navView para navegación del menú lateral ------ //
        val navView: NavigationView = view.findViewById(R.id.nav_view)
        //------ Se implementa el menú desplegable lateral ------ //
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_observaciones)
        val drawerLayout: DrawerLayout = view.findViewById(R.id.drawerObservaciones)
        toggle = ActionBarDrawerToggle(
            this.requireContext() as AppCompatActivity,
            drawerLayout, toolbar, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationOnClickListener {
            if (drawerLayout.isDrawerOpen((GravityCompat.START))) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
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
        // ------ Se agrega la funcionalidad del botón ------ //
        siguienteButton.setOnClickListener{
            Formulario.observ = observaciones.text.toString()
            findNavController().navigate(R.id.action_fragment_observaciones_to_fragment_ingreso)
        }
        return view
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