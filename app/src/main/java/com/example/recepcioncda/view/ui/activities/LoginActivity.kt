package com.example.recepcioncda.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.recepcioncda.R

class LoginActivity : AppCompatActivity() {

    lateinit var ingresarbutton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ingresarbutton = findViewById(R.id.botonIngresar) as Button

        ingresarbutton.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}