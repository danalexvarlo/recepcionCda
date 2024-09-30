package com.example.recepcioncda.view.ui.fragments


import android.content.Intent
import android.os.Bundle
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
import org.json.JSONArray
import org.json.JSONException
import java.net.URLEncoder

lateinit var nombreRecepcionista:TextView //Nombre del recepcionista

private val URL = "http://192.168.0.113/recepcion/fetch.php"
private lateinit var requestQueue: RequestQueue
//DATOS DEL CONDUCTOR
private lateinit var nombre: EditText
private lateinit var tipoDocumento: EditText
private lateinit var documento: EditText
private lateinit var direccion: EditText
private lateinit var telefono: EditText
private lateinit var correo: EditText
//DATOS DEL VEHICULO

private lateinit var radioGroupEntrada: RadioGroup
private lateinit var primeraVez: RadioButton
private lateinit var segundaVez: RadioButton

// Listas para RadioButtons y RadioGroups
private val radioButtonsSi = mutableListOf<RadioButton>()
private val radioButtonsNo = mutableListOf<RadioButton>()
private val radioGroups = mutableListOf<RadioGroup>()

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

        nombre = view.findViewById(R.id.editNombreConductor)
        tipoDocumento = view.findViewById(R.id.editTipodoctConductor)
        documento = view.findViewById(R.id.editDocumentoConductor)
        direccion = view.findViewById(R.id.editDireccionConductor)
        telefono = view.findViewById(R.id.editTelefonoConductor)
        correo = view.findViewById(R.id.editCorreoConductor)

        nombreRecepcionista = view.findViewById(R.id.nombreRecepcionista)
        nombreRecepcionista.text = Usuario.nombre ?: "Usuario desconocido"
        noDiscapacidad = view.findViewById(R.id.radioNoDiscapacidad)

        val navView: NavigationView = view.findViewById(R.id.nav_view)

        // Se implementa el menú desplegable lateral
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
        inicializarRadioButtons(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nextFormulario = view.findViewById<Button>(R.id.siguienteButton)
        nextFormulario.setOnClickListener() {
            // Se guardan los datos del conductor en un modelo temporal
            Conductor.nombre = nombre.text.toString()
            Conductor.tipoDocumento = tipoDocumento.text.toString()
            Conductor.idDriver = documento.text.toString().toFloatOrNull()
            Conductor.adress = direccion.text.toString()
            Conductor.phone = telefono.text.toString()
            Conductor.email = correo.text.toString()
            // Se guardan los datos del vehículo en un modelo temporal
            findNavController().navigate(R.id.action_formulario_to_livianoFragment)
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
                "Perdida auditiva leve PAL",
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

    private fun agregarDatos() {

    }

    private fun cerrarSesion() { //Función para cerrar sesión y volver al login
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
            val radioGroupId =
                resources.getIdentifier("radioGroup$i", "id", requireContext().packageName)
            val pautaSiId =
                resources.getIdentifier("pauta${i}Si", "id", requireContext().packageName)
            val pautaNoId =
                resources.getIdentifier("pauta${i}No", "id", requireContext().packageName)

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

    private fun setupSpinners(view: View, spinnerId: Int, options: Array<String>) {
        val spinner: Spinner = view.findViewById(spinnerId)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedOption = parent.getItemAtPosition(position) as String
                when (spinnerId) {
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
}