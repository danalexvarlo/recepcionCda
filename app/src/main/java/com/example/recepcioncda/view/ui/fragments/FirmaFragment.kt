package com.example.recepcioncda.view.ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.telecom.Call
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.activities.LoginActivity
import com.example.recepcioncda.view.ui.models.Conductor
import com.example.recepcioncda.view.ui.models.Formulario
import com.example.recepcioncda.view.ui.models.SignatureView
import com.example.recepcioncda.view.ui.models.Usuario
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.KMutableProperty0
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException

private lateinit var corregir: Button
private lateinit var guardarDatos: Button
class FirmaFragment : Fragment() {
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var signatureView: SignatureView
    private lateinit var signatureViewDos: SignatureView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_firma, container, false)
        //------ Se implementa el navView para navegación del menú lateral ------ //
        nombreRecepcionista = view.findViewById(R.id.nombreRecepcionistaFirmas)
        nombreRecepcionista.text = Usuario.nombre?: "Usuario desconocido"
        signatureView = view.findViewById(R.id.signature_view)
        signatureViewDos = view.findViewById(R.id.signature_view_dos)// Añade esta línea
        val navView: NavigationView = view.findViewById(R.id.nav_view)
        //------ Se implementa el botón para corregir la firma ------ //
        corregir = view.findViewById(R.id.corregirFirmaButton)
        corregir.setOnClickListener{ signatureView.clear()
                                     signatureViewDos.clear()   }
        //------ Se implementa el menú desplegable lateral ------ //
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_ingreso)
        val drawerLayout: DrawerLayout = view.findViewById(R.id.drawerLayoutFirmas)
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
        //------ Se implementa el botón para guardar todos los datos ------ //
        guardarDatos = view.findViewById(R.id.guardarDatosButton)
        guardarDatos.setOnClickListener{
            saveSignature(signatureView, Formulario::firmaUno)
            saveSignature(signatureView, Formulario::firmaDos)
        }
        return view
    }

    private fun obtenerFechaActual(): String {
        val fechaFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return fechaFormat.format(Date())
    }

    fun obtenerHoraActual(): String {
        val horaFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return horaFormat.format(Date())
    }

    private fun cerrarSesion() { //Función para cerrar sesión y volver al login
        Usuario.nombre = null  // Se limpia la variable global del usuario
        //findNavController().navigate(R.id.loginActivity)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun saveSignature(signatureView: SignatureView, signatureVariable: KMutableProperty0<Bitmap?>) {
        // Obtén el bitmap de la firma
        val bitmap = signatureView.getSignatureBitmap()

        if (bitmap != null) {
            // Guarda el bitmap en la variable global correspondiente
            signatureVariable.set(bitmap)
            Toast.makeText(requireContext(), "Firma guardada en memoria", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "No hay firma para guardar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enviarDatosAlServidor() {
        // Crear un cliente OkHttp
        val client = OkHttpClient()

        // Crear el formulario con los datos
        val formBody = FormBody.Builder()
            .add("fecha", obtenerFechaActual() ?: "")
            .add("entrada", Formulario.entrada ?: "") // Cambia esto por el valor real
            .add("hora_ent", obtenerHoraActual() ?: "")
            .add("cedula_id", (Conductor.idDriver ?: "") as String) // Cambia esto por el valor real
            .add("condicion_1", Formulario.cond1 ?: "") // Cambia por los valores reales
            .add("condicion_2", Formulario.cond2 ?: "")
            .add("condicion_3", Formulario.cond3 ?: "")
            .add("condicion_4", Formulario.cond4 ?: "")
            .add("condicion_5", Formulario.cond5 ?: "")
            .add("condicion_6", Formulario.cond6 ?: "")
            .add("condicion_7", Formulario.cond7 ?: "")
            .add("condicion_8", Formulario.cond8 ?: "")
            .add("condicion_9", Formulario.cond9 ?: "")
            .add("condicion_10", Formulario.cond10 ?: "")
            .add("condicion_11", Formulario.cond11 ?: "")
            .add("condicion_12", Formulario.cond12 ?: "")
            .add("condicion_13", Formulario.cond13 ?: "")
            .add("condicion_14", Formulario.cond14 ?: "")
            .add("condicion_15", Formulario.cond15 ?: "")
            .add("condicion_16", Formulario.cond16 ?: "")
            .add("condicion_17", Formulario.cond17 ?: "")
            .add("presiond_de", (Formulario.presiondDe ?: "") as String)
            .add("presiond_iz", (Formulario.presiondIz ?: "") as String)
            .add("presiont_de", (Formulario.presiontDe ?: "") as String)
            .add("presiont_iz", (Formulario.presiondIz ?: "") as String)
            .add("repuesto", (Formulario.presionRep ?: "") as String)
            // Agrega el resto de condiciones aquí
            .add("firma_1cond", Formulario.firmaUno?.let { bitmapToBase64(it) } ?: "")
            .add("firma_2cond", Formulario.firmaDos?.let { bitmapToBase64(it) } ?: "")
            .build()

        // Crear la solicitud POST
        val request = Request.Builder()
            .url("http://tu-servidor.com/ruta/a/tu/script.php") // Cambia la URL por la de tu servidor
            .post(formBody)
            .build()

        // Ejecutar la solicitud
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // Manejar error de red aquí
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // Manejar respuesta exitosa
                    val respuesta = response.body?.string()
                    Log.d("Respuesta", respuesta ?: "Sin respuesta")
                } else {
                    // Manejar error en la respuesta
                    Log.e("Error", "Código de respuesta: ${response.code}")
                }
            }
        })
    }
    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

}