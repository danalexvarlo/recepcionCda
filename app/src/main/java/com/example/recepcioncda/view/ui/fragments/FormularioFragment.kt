package com.example.recepcioncda.view.ui.fragments


import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.recepcioncda.R
import com.example.recepcioncda.view.ui.activities.LoginActivity
import com.example.recepcioncda.view.ui.models.Usuario
import com.google.android.material.navigation.NavigationView
import com.example.recepcioncda.view.ui.models.Conductor
import com.example.recepcioncda.view.ui.models.Vehiculo
import com.example.recepcioncda.view.ui.models.Formulario
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import java.net.URLEncoder
import java.text.Normalizer.Form
import java.text.SimpleDateFormat
import java.util.Locale

lateinit var nombreRecepcionista:TextView //Nombre del recepcionista

private val URL = "http://192.168.0.113/recepcion/fetch.php"
private lateinit var requestQueue: RequestQueue
//Se inicializan las variables para el número del formulario siguiente
private lateinit var formularioSiguiente: TextView
// RadioGroups para los RadioButton de entrada por primera o segunda vez
private lateinit var radioGroupEntrada: RadioGroup
private lateinit var primeraVez: RadioButton
private lateinit var segundaVez: RadioButton
//DATOS DEL CONDUCTOR
private lateinit var nombre: EditText
private lateinit var tipoDocumento: Spinner
private lateinit var documento: EditText
private lateinit var direccion: EditText
private lateinit var telefono: EditText
private lateinit var correo: EditText
//DATOS DEL VEHICULO
private lateinit var kilometraje: EditText
private lateinit var placa: EditText
private lateinit var modeloVehiculo: EditText
private lateinit var marca: EditText
private lateinit var color: EditText
private lateinit var soat: EditText
private lateinit var potencia: EditText
private lateinit var numPasajeros: EditText
// RadioGroup para blindaje del vehiulo
private lateinit var blindado: RadioGroup
private lateinit var blindadoSi: RadioButton
private lateinit var blindadoNo: RadioButton
//RadioGroup para verifica si el vehículo viene convertido a Gas
private lateinit var radioGroupGas: RadioGroup
private lateinit var vigenciaGas: EditText
private lateinit var radioButtonSi: RadioButton
private lateinit var radioButtonNo: RadioButton
private lateinit var radioButtonNoAplica: RadioButton
private lateinit var vigencia: TextView
// RadioGroup para verificar si el conductor tiene su licencia de tránsito
private lateinit var licenciaTransito: RadioGroup
private lateinit var licenciaSi: RadioButton
private lateinit var licenciaNo: RadioButton
// Se inicializan los radioGroup de las discapacidades
private lateinit var radioGroupDiscapacidades: RadioGroup
private lateinit var siDiscapacidad: RadioButton
private lateinit var noDiscapacidad: RadioButton

class FormularioFragment : Fragment() {

    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_formulario, container, false)
        requestQueue = Volley.newRequestQueue(requireContext())
        // Agrego al fragmento el número del siguiente formato
        formularioSiguiente = view.findViewById(R.id.numeroFormulario)
        obtenerSiguienteFormato { siguienteFormato ->
            formularioSiguiente.text = siguienteFormato.toString()
        }
        // ------ Se inicializa la fecha de vigencia del gas ------ //
        vigencia = view.findViewById(R.id.vigenciaText)
        //------ Se inicializan las variables de los datos del conductor ------ //
        nombre = view.findViewById(R.id.editNombreConductor)
        tipoDocumento = view.findViewById(R.id.spinnerTipodoctConductor)
        documento = view.findViewById(R.id.editDocumentoConductor)
        documento.transformationMethod = null
        direccion = view.findViewById(R.id.editDireccionConductor)
        telefono = view.findViewById(R.id.editTelefonoConductor)
        telefono.transformationMethod = null
        correo = view.findViewById(R.id.editCorreoConductor)
        //------ Se inicializan las variables de los datos del vehículo ------ //
        kilometraje = view.findViewById(R.id.editKilometrajeVehiculo)
        kilometraje.transformationMethod = null
        placa = view.findViewById(R.id.editPlacaVehiculo)
        modeloVehiculo = view.findViewById(R.id.editModeloVehiculo)
        modeloVehiculo.transformationMethod = null
        marca = view.findViewById(R.id.editMarcaVehiculo)
        color = view.findViewById(R.id.editColorVehiculo)
        soat = view.findViewById(R.id.editSoatVehiculo)
        potencia = view.findViewById(R.id.editPotenciaVehiculo)
        potencia.transformationMethod = null
        numPasajeros = view.findViewById(R.id.editNoPasajeros)
        numPasajeros.transformationMethod = null
        blindado = view.findViewById(R.id.radioGroupBlindaje)
        //------ Edita el editText del soat al seleccionar permita ingresar el SOAT ------ //
        soat.setOnClickListener{ mostrarFechaSoat() }
        // ------ Edita el editText de la vigencia al seleccionar permita ingresar la fecha ------ //
        vigenciaGas = view.findViewById(R.id.editVigenciaGas)
        vigenciaGas.setOnClickListener{ mostrarVigenciaGas()
                                        Vehiculo.fechaGas = vigenciaGas.text.toString()}
        // ------ Se inicializan los RadioButton para vehiculos convertido a gas ------ //
        radioGroupGas = view.findViewById(R.id.radioGroupCertificadoGas)
        // ------ Se inicializan los RadioButton para el ingreso ------ //
        radioGroupEntrada = view.findViewById(R.id.radioGroupEntrada)
        // ------ Se inicializan los RadioButton para licencia de transito ------ //
        licenciaTransito = view.findViewById(R.id.radioGroupLicencia)
        //------ Se inicializan las variables para mostrar el nombre del recepcionista ------ //
        nombreRecepcionista = view.findViewById(R.id.nombreRecepcionista)
        nombreRecepcionista.text = Usuario.nombre ?: "Usuario desconocido"
        //------ Se inicializan los radioButton para seleccionar si el conductor tiene discapacidades ------ //
        radioGroupDiscapacidades = view.findViewById(R.id.radioGroupDiscapacidades)
        noDiscapacidad = view.findViewById(R.id.radioNoDiscapacidad)
        //------ Se implementa el navView para navegación del menú lateral ------ //
        val navView: NavigationView = view.findViewById(R.id.nav_view)
        //------ Se implementa el menú desplegable lateral ------ //
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_formulario)
        val drawerLayout: DrawerLayout = view.findViewById(R.id.formulario_fragment)
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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        for (i in 1..17) {
            inicializarRadioGroup(view, i)
        }
        vigencia.setOnClickListener{ Toast.makeText(requireContext(),Vehiculo.fechaGas, Toast.LENGTH_SHORT).show() }
        val nextFormulario = view.findViewById<Button>(R.id.siguienteButton)
        nextFormulario.setOnClickListener() {
            // Se guardan los datos del conductor en un modelo temporal
            Conductor.nombre = nombre.text.toString()
            Conductor.idDriver = documento.text.toString().toFloatOrNull()
            Conductor.adress = direccion.text.toString()
            Conductor.phone = telefono.text.toString()
            Conductor.email = correo.text.toString()
            // Se guardan los datos del vehículo en un modelo temporal
            Vehiculo.kilometraje = kilometraje.text.toString().toFloatOrNull()
            Vehiculo.placa = placa.text.toString()
            Vehiculo.marca = marca.text.toString()
            Vehiculo.modelo = modeloVehiculo.text.toString().toIntOrNull()
            Vehiculo.color = color.text.toString()
            Vehiculo.soat = soat.text.toString()
            Vehiculo.potencia = potencia.text.toString().toIntOrNull()
            Vehiculo.numPasaj = numPasajeros.text.toString().toIntOrNull()
            Vehiculo.fechaGas = vigenciaGas.text.toString()
            Toast.makeText(requireContext(), Vehiculo.fechaGas, Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), Formulario.entrada, Toast.LENGTH_SHORT).show()
            Log.d("Formulario", "Datos Vehiculo: ${Vehiculo.fechaGas}")
            if(Vehiculo.clasVeh == "Motocarro"){
                findNavController().navigate(R.id.action_formulario_to_motocarroFragment)
            }
            else if(Vehiculo.clasVeh == "Motocicleta"){
                findNavController().navigate(R.id.action_formulario_to_motosFragment)
            }
            else if(Vehiculo.clasVeh in listOf("Automóvil", "Camioneta", "Campero", "Microbus", "Otro")){
                findNavController().navigate(R.id.action_formulario_to_livianoFragment)
            }
            Log.d("Formulario", "Datos Vehiculo: ${Vehiculo.clasVeh}")
        }
        // Se guarda el porte de la licencia en una variable global
        licenciaTransito.setOnCheckedChangeListener{group, checkedId ->
            licenciaSi = view.findViewById(R.id.siLicenciaTransito)
            licenciaNo = view.findViewById(R.id.noLicenciaTransito)
            when(checkedId){
                R.id.siLicenciaTransito -> { Vehiculo.licTrans = licenciaSi.text.toString() }
                R.id.noLicenciaTransito -> { Vehiculo.licTrans = licenciaNo.text.toString() }
            }
        }
        // Se guarda el blindaje en una variable global
        blindado.setOnCheckedChangeListener{group, checkedId ->
            blindadoSi = view.findViewById(R.id.siBlindado)
            blindadoNo = view.findViewById(R.id.noBlindado)
            when(checkedId){
                R.id.siBlindado -> { Vehiculo.blindado = blindadoSi.text.toString()}
                R.id.noBlindado -> { Vehiculo.blindado = blindadoNo.text.toString()}
            }
        }
        // Se guarda el ingreso en una variable global
        radioGroupEntrada.setOnCheckedChangeListener{group, checkedId ->
            primeraVez = view.findViewById(R.id.primeraVez)
            segundaVez = view.findViewById(R.id.segundaVez)
            when(checkedId){ // Verifica cuál RadioButton está seleccionado
                R.id.primeraVez -> { Formulario.entrada = primeraVez.text.toString() }
                R.id.segundaVez -> { Formulario.entrada = segundaVez.text.toString() }
            }
        }
        radioGroupDiscapacidades.setOnCheckedChangeListener{group, checkedId ->
            siDiscapacidad = view.findViewById(R.id.radioSiDiscapacidad)
            noDiscapacidad = view.findViewById(R.id.radioNoDiscapacidad)
            when(checkedId){ // Verifica cuál RadioButton está seleccionado
                R.id.radioSiDiscapacidad -> { Formulario.discapacidades = siDiscapacidad.text.toString() }
                R.id.radioNoDiscapacidad -> { Formulario.discapacidades = noDiscapacidad.text.toString() }
            }
        }
        // Se guarda si el vehículo es convertido a gas
        radioGroupGas.setOnCheckedChangeListener{group, checkedId ->
            radioButtonSi = view.findViewById(R.id.siCertificadoGas)
            radioButtonNo = view.findViewById(R.id.noCertificadoGas)
            radioButtonNoAplica = view.findViewById(R.id.noAplicaCertificadoGas)
            when(checkedId){
                R.id.siCertificadoGas -> { Vehiculo.vehGas = radioButtonSi.text.toString()
                    vigenciaGas.visibility = View.VISIBLE
                    vigenciaGas.isEnabled = true
                    Vehiculo.fechaGas = vigenciaGas.text.toString()}
                R.id.noCertificadoGas -> { Vehiculo.vehGas = radioButtonNo.text.toString()
                    vigenciaGas.visibility = View.VISIBLE
                    vigenciaGas.isEnabled = true
                    Vehiculo.fechaGas = vigenciaGas.text.toString()}
                R.id.noAplicaCertificadoGas -> { Vehiculo.vehGas = radioButtonNoAplica.text.toString()
                    vigenciaGas.visibility = View.GONE
                    vigenciaGas.isEnabled = false
                    Vehiculo.fechaGas = null}
            }
            Log.d("Formulario", "Datos Vehiculo: ${Vehiculo.fechaGas}")
        }
        var spinnerDiscapacidades = view.findViewById<Spinner>(R.id.spinnerDiscapacidadAuditiva)
        noDiscapacidad.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                spinnerDiscapacidades.visibility = View.GONE
                spinnerDiscapacidades.isEnabled = false
            } else {
                spinnerDiscapacidades.visibility = View.VISIBLE
                spinnerDiscapacidades.isEnabled = true
            }
        }
        setupSpinners(
            view, R.id.spinnerTipodoctConductor, arrayOf(
                "Cédula de ciudadanía", "Cédula de extranjería", "Otro"
            )
        )
        setupSpinners(
            view, R.id.spinnerTipoCombustible, arrayOf(
                "Gasolina", "Gas/Gasolina", "Diesel",
                "Eléctrico", "Híbrido", "Otro"
            )
        )
        setupSpinners(
            view, R.id.spinnerTipoServicio, arrayOf(
                "Particular", "Público", "Oficial",
                "Taxi", "Enseñanza", "Otro"
            )
        )
        setupSpinners(
            view, R.id.spinnerTarjetaOperacional, arrayOf(
                "No aplica", "Nacional", "Urbano",
                "Turismo", "Escolar"
            )
        )
        setupSpinners(
            view, R.id.spinnerDiscapacidadAuditiva, arrayOf(
                "No aplica", "Perdida auditiva leve PAL",
                "Perdida auditiva moderada PAM", "Perdida auditiva moderadamente severa PAMS",
                "Perdida auditiva grave PAG", "Perdida auditiva profunda PAP"
            )
        )
        setupSpinners(
            view, R.id.spinnerTipoVehiculo, arrayOf(
                "Motocicleta", "Motocarro", "Automóvil",
                "Campero", "Camioneta", "Microbús", "Otro"
            )
        )
        setupSpinners(
            view, R.id.spinnerTipoMotorMotocicleta, arrayOf(
                "No aplica", "4 Tiempos",
                "2 Tiempos"
            )
        )
    }
    private fun cerrarSesion() { //Función para cerrar sesión y volver al login
        Usuario.nombre = null  // Se limpia la variable global del usuario
        //findNavController().navigate(R.id.loginActivity)
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun inicializarRadioGroup(view: View, index: Int) {
        val radioGroupId = resources.getIdentifier("radioGroup$index", "id", requireContext().packageName)
        val radioGroup: RadioGroup? = view.findViewById(radioGroupId)

        radioGroup?.setOnCheckedChangeListener { _, checkedId ->
            val selectedText = when (checkedId) {
                resources.getIdentifier("pauta${index}SiButton", "id", requireContext().packageName) -> "Sí"
                resources.getIdentifier("pauta${index}NoButton", "id", requireContext().packageName) -> "No Aplica"
                else -> null
            }
            Log.d("RadioGroup", "Selected: $selectedText for index: $index")
            when (index) {
                // Índices para agregar las variables del modelo Formulario
                1 -> Formulario.cond1 = selectedText
                2 -> Formulario.cond2 = selectedText
                3 -> Formulario.cond3 = selectedText
                4 -> Formulario.cond4 = selectedText
                5 -> Formulario.cond5 = selectedText
                6 -> Formulario.cond6 = selectedText
                7 -> Formulario.cond7 = selectedText
                8 -> Formulario.cond8 = selectedText
                9 -> Formulario.cond9 = selectedText
                10 -> Formulario.cond10 = selectedText
                11 -> Formulario.cond11 = selectedText
                12 -> Formulario.cond12 = selectedText
                13 -> Formulario.cond13 = selectedText
                14 -> Formulario.cond14 = selectedText
                15 -> Formulario.cond15 = selectedText
                16 -> Formulario.cond16 = selectedText
                17 -> Formulario.cond17 = selectedText
            }
        }
    }
    private fun setupSpinners(view: View, spinnerId: Int, options: Array<String>) {
        val spinner: Spinner = view.findViewById(spinnerId)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position) as String
                when (spinnerId) {
                    R.id.spinnerTipodoctConductor -> {
                        Conductor.tipoDocumento = selectedOption
                    }
                    R.id.spinnerTipoCombustible -> {
                        Vehiculo.tipoComb = selectedOption
                    }
                    R.id.spinnerTipoServicio -> {
                        Vehiculo.tipoServ = selectedOption
                    }
                    R.id.spinnerTarjetaOperacional -> {
                        Vehiculo.tarjOp = selectedOption
                    }
                    R.id.spinnerTipoVehiculo -> {
                        Vehiculo.clasVeh = selectedOption
                    }
                    R.id.spinnerDiscapacidadAuditiva -> {
                        Conductor.disc_licencia = selectedOption
                    }
                    R.id.spinnerTipoMotorMotocicleta -> {
                        Vehiculo.tipoMotor = selectedOption
                    }
                    // Agrega más casos según sea necesario
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun mostrarFechaSoat() {
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Crear un objeto Calendar con la fecha seleccionada
                val fechaSeleccionada = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }

                // Formatear la fecha a "yyyy-MM-dd"
                val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val fechaFormateada = formatoFecha.format(fechaSeleccionada.time)

                // Establecer la fecha formateada en el EditText
                soat.setText(fechaFormateada)
            },
            year, mes, dia
        )
        datePickerDialog.show()
    }

    private fun mostrarVigenciaGas(){
        val calendario = Calendar.getInstance()
        val year = calendario.get(Calendar.YEAR)
        val mes = calendario.get(Calendar.MONTH)
        val dia = calendario.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                // Crear un objeto Calendar con la fecha seleccionada
                val fechaSeleccionada = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                }

                // Formatear la fecha a "yyyy-MM-dd"
                val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val fechaFormateada = formatoFecha.format(fechaSeleccionada.time)

                // Establecer la fecha formateada en el EditText
                vigenciaGas.setText(fechaFormateada)
            },
            year, mes, dia)
        datePickerDialog.show()
    }

    //Consulta el último formato hecho y le suma uno
    private fun obtenerSiguienteFormato(callback: (Int) -> Unit) {
        // Aquí harías la solicitud para obtener el siguiente formato
        val urlUltimoFormato = "https://70a2-186-117-205-2.ngrok-free.app/recepcion/fetch.php" // Cambia esto a tu URL real

        val stringRequest = StringRequest(
            Request.Method.GET,
            urlUltimoFormato,
            Response.Listener<String> { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val ultimoFormato = jsonResponse.getInt("ultimo_formato")
                    // Almacena o utiliza el último formato más uno según sea necesario
                    Formulario.num_formato = ultimoFormato.toString().toFloatOrNull() ?: 0f
                    Log.d("UltimoFormato", "El siguiente formulario es: $ultimoFormato")

                    // Llama al callback con el siguiente formato
                    callback(ultimoFormato)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al procesar el último formato", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Log.e("VolleyError", error.toString())
                Toast.makeText(requireContext(), "Error al obtener el último formato: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(stringRequest)
    }
}