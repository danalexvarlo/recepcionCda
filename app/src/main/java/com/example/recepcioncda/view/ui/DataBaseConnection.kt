package com.example.recepcioncda.view.ui

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object dataBaseConnection {
    private const val URL = "jdbc:mysql://10.0.0.2:3306/recepcion" // Cambia 'recepcion' por el nombre de tu base de datos
    private const val USER = "root" // Cambia esto por tu usuario
    private const val PASSWORD = "901012825" // Cambia esto por tu contraseña
    var connection: Connection? = null
    // Método para obtener la conexión a la base de datos


    // Método para probar la conexión

}