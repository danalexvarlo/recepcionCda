package com.example.recepcioncda.view.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.activities.LoginActivity
import com.example.recepcioncda.view.ui.models.Usuario
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONException
import java.net.URLEncoder

lateinit var nombreRecepcionista:TextView //Nombre del recepcionista

private val URL = "http://192.168.0.113/recepcion/fetch.php"
private lateinit var requestQueue: RequestQueue

private lateinit var radioGroupEntrada: RadioGroup
private lateinit var primeraVez: RadioButton
private lateinit var segundaVez: RadioButton

// Listas para RadioButtons y RadioGroups
private val radioButtonsSi = mutableListOf<RadioButton>()
private val radioButtonsNo = mutableListOf<RadioButton>()
private val radioGroups = mutableListOf<RadioGroup>()

class FormularioFragment : Fragment() {

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_formulario, container, false)

        requestQueue = Volley.newRequestQueue(requireContext())

        nombreRecepcionista = view.findViewById(R.id.nombreRecepcionista)
        nombreRecepcionista.text = Usuario.nombre ?: "Usuario desconocido"

        val navView : NavigationView = view.findViewById(R.id.nav_view)

        // Se implementa el menú desplegable lateral
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_formulario)
        val drawerLayout : DrawerLayout = view.findViewById(R.id.formulario_fragment)
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

        navView.setNavigationItemSelectedListener(){
            when(it.itemId){
                R.id.homeBar -> { //Item para regresar al Home
                    findNavController().navigate(R.id.homeFragment)
                    Toast.makeText(context, "Menú principal", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    cerrarSesion()
                    Toast.makeText(context, "Cerrar sesión", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nextFormulario = view.findViewById<Button>(R.id.siguienteButton)
        nextFormulario.setOnClickListener() {
            findNavController().navigate(R.id.action_formulario_to_livianoFragment)
        }
    }

    private fun fetchData(usuarioText: String) {
        //val usuarioText = nombreRecep.usuario.text.toString()
        val urlWithParams = "$URL?usuario=${URLEncoder.encode(usuarioText, "UTF-8")}"
        val stringRequest = StringRequest(
            Request.Method.GET,
            urlWithParams,
            Response.Listener<String> { response ->
                Log.d("RawResponse", response)
                try {
                    val cleanedResponse = response.trim()
                    if (cleanedResponse.startsWith("[")) {
                        val jsonResponse = JSONArray(cleanedResponse)
                        if (jsonResponse.length() > 0) {
                            val firstObject = jsonResponse.getJSONObject(0)
                            val nombre = firstObject.getString("nombre")
                            nombreRecepcionista.setOnClickListener{
                                Toast.makeText(requireContext(), nombre, Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "No hay datos disponibles", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error en los datos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                error.printStackTrace()
                Toast.makeText(requireContext(), "Error de red: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(stringRequest)
    }

    private fun cerrarSesion(){ //Función para cerrar sesión y volver al login
        Usuario.nombre = null  // Se limpia la variable global del usuario
        //findNavController().navigate(R.id.loginActivity)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun inicializarRadioButtons(view: View) {
        // RadioButtons de ingreso de vehículo
        primeraVez = view.findViewById(R.id.primeraVez)
        segundaVez = view.findViewById(R.id.segundaVez)

        // Verifica si los RadioButtons fueron encontrados
        if (primeraVez == null || segundaVez == null) {
            Log.e("FormularioFragment", "RadioButtons no encontrados")
            return
        }

        // RadioButton para especificar blindaje de vehículo
        val siBlindado: RadioButton? = view.findViewById(R.id.siBlindado)
        val noBlindado: RadioButton? = view.findViewById(R.id.noBlindado)

        // Asegúrate de que estos elementos existan
        if (siBlindado == null || noBlindado == null) {
            Log.e("FormularioFragment", "RadioButtons de blindaje no encontrados")
            return
        }

        // Añadir a las listas
        radioButtonsSi.add(siBlindado)
        radioButtonsNo.add(noBlindado)

        // Pautas de ingreso
        for (i in 1..17) {
            val radioGroupId = resources.getIdentifier("radioGroup$i", "id", requireContext().packageName)
            val pautaSiId = resources.getIdentifier("pauta${i}Si", "id", requireContext().packageName)
            val pautaNoId = resources.getIdentifier("pauta${i}No", "id", requireContext().packageName)

            val radioGroup: RadioGroup? = view.findViewById(radioGroupId)
            val pautaSi: RadioButton? = view.findViewById(pautaSiId)
            val pautaNo: RadioButton? = view.findViewById(pautaNoId)

            // Asegúrate de que los elementos no sean null
            if (radioGroup != null && pautaSi != null && pautaNo != null) {
                radioGroups.add(radioGroup)
                radioButtonsSi.add(pautaSi)
                radioButtonsNo.add(pautaNo)
            } else {
                Log.e("FormularioFragment", "Elementos de pautas no encontrados para el índice $i")
            }
        }

        // RadioButton para documentos necesarios
        val radioGroupDocsNecesarios: RadioGroup? = view.findViewById(R.id.radioGroupLicencia)
        val docsNecesariosSi: RadioButton? = view.findViewById(R.id.siLicenciaTransito)
        val docsNecesariosNo: RadioButton? = view.findViewById(R.id.noLicenciaTransito)

        if (radioGroupDocsNecesarios != null && docsNecesariosSi != null && docsNecesariosNo != null) {
            radioGroups.add(radioGroupDocsNecesarios)
            radioButtonsSi.add(docsNecesariosSi)
            radioButtonsNo.add(docsNecesariosNo)
        } else {
            Log.e("FormularioFragment", "RadioButtons de documentos no encontrados")
        }
    }
}