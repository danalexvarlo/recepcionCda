package com.example.recepcioncda.view.ui.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recepcioncda.R
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var ingresarbutton : Button
    lateinit var usuario : EditText
    lateinit var contrasena : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ingresarbutton = findViewById(R.id.botonIngresar) as Button
        usuario = findViewById(R.id.user) as EditText
        contrasena = findViewById(R.id.password) as EditText



        ingresarbutton.setOnClickListener{

        }
    }

    private fun validarUsuario(URL: String) {
        val stringRequest = object : StringRequest(
            Request.Method.POST, URL,
            Response.Listener<String> { response ->
                if (response.isNotEmpty()) {
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                // Handle error response
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String>? {
                return super.getParams()
            }
        }

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest)
    }
}