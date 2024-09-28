package com.example.recepcioncda.view.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.fragments.FormularioFragment
import com.example.recepcioncda.view.ui.models.Usuario
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder

class LoginActivity : AppCompatActivity() {

    lateinit var ingresarbutton: Button
    lateinit var usuario: EditText
    lateinit var contrasena: EditText

    private val URL1 = "http://192.168.0.108/recepcion/fetch.php"
    private lateinit var requestQueue: RequestQueue

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        requestQueue = Volley.newRequestQueue(this)

        ingresarbutton = findViewById(R.id.botonIngresar)
        usuario = findViewById(R.id.user)
        contrasena = findViewById(R.id.password)

        ingresarbutton.setOnClickListener {
            if (isNetworkAvailable()) {
                val usuarioText = usuario.text.toString()
                val urlWithParams = "$URL1?usuario=${URLEncoder.encode(usuarioText, "UTF-8")}"

                val stringRequest = StringRequest(
                    Request.Method.GET,
                    urlWithParams,
                    Response.Listener<String> { response ->
                        Log.d("RawResponse", response) // Registra la respuesta cruda
                        try {
                            // Verifica si la respuesta comienza con un JSON válido
                            val cleanedResponse = response.trim()
                            if (cleanedResponse.startsWith("[")) {
                                val jsonResponse = JSONArray(cleanedResponse)
                                if (jsonResponse.length() > 0) {
                                    val firstObject = jsonResponse.getJSONObject(0)
                                    val password = firstObject.getString("contrasena")
                                    if (password == contrasena.text.toString()) {
                                        Usuario.nombre = firstObject.getString("nombre")
                                        Toast.makeText(baseContext, "INICIO DE SESIÓN EXITOSO", Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, HomeActivity::class.java))
                                    } else {
                                        Toast.makeText(baseContext, "USUARIO O CONTRASEÑA INCORRECTOS", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(baseContext, "No hay datos disponibles", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(baseContext, "USUARIO O CONTRASEÑA INCORRECTOS", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(baseContext, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.e("VolleyError", error.toString())
                        error.printStackTrace()
                        Toast.makeText(baseContext, "Error de red: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                )

                stringRequest.retryPolicy = DefaultRetryPolicy(
                    5000, // Tiempo de espera en milisegundos (15 segundos)
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )

                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(baseContext, "No hay conexión a Internet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }
            networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo?.isConnected == true
        }
    }

}

