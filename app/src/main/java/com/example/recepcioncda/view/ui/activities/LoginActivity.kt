package com.example.recepcioncda.view.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.dataBaseConnection
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var ingresarbutton: Button
    lateinit var usuario: EditText
    lateinit var contrasena: EditText

    val URL1 = "http://192.168.0.113/recepcion/consulta.php"
    val requestQueue: RequestQueue? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val requestQueue = Volley.newRequestQueue(this)

        ingresarbutton = findViewById(R.id.botonIngresar) as Button
        usuario = findViewById(R.id.user) as EditText
        contrasena = findViewById(R.id.password) as EditText

        ingresarbutton.setOnClickListener {
            val stringRequest = object : StringRequest(
                Method.POST,
                URL1,
                Response.Listener<String> { response ->

                },
                Response.ErrorListener { error ->

                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["usuario"] = usuario.text.toString() // Sustituye con el nombre de usuario real
                    params["contrasena"] = contrasena.text.toString() // Sustituye con la contraseña real
                    return params
                }
            }
            requestQueue.add(stringRequest)
        }
    }
}

