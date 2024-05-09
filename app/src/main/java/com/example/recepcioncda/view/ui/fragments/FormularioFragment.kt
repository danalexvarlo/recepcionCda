package com.example.recepcioncda.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.RecyclerView
import com.example.recepcioncda.R
/**
// RadioButton de ingreso de vehículo
var primeraVez:RadioButton? = null;
var segundaVez:RadioButton? = null;
var entradaGroup:RadioGroup? = null;

// RadioButton para especificar blindaje de vehiculo
var siBlindado:RadioButton? = null;
var noBlindado:RadioButton? = null;
var blindajeGroup:RadioGroup? = null;

// RadioButton para las pautas de ingreso requeridas del vehículo
var pautaUnoSi:RadioButton? = null; var pautaUnoNo:RadioButton? = null;
var radioGroupUno:RadioGroup? = null;

var pautaDosSi:RadioButton? = null; var pautaDosNo:RadioButton? = null;
var radioGroupDos:RadioGroup? = null;

var pautaTresSi:RadioButton? = null; var pautaTresNo:RadioButton? = null;
var radioGroupTres:RadioGroup? = null;

var pautaCuatroSi:RadioButton? = null; var pautaCuatroNo:RadioButton? = null;
var radioGroupCuatro:RadioGroup? = null;

var pautaCincoSi:RadioButton? = null; var pautaCincoNo:RadioButton? = null;
var radioGroupCinco:RadioGroup? = null;

var pautaSeisSi:RadioButton? = null; var pautaSeisNo:RadioButton? = null;
var radioGroupSeis:RadioGroup? = null;

var pautaSieteSi:RadioButton? = null; var pautaSieteNo:RadioButton? = null;
var radioGroupSiete:RadioGroup? = null;

var pautaOchoSi:RadioButton? = null; var pautaOchoNo:RadioButton? = null;
var radioGroupOcho:RadioGroup? = null;

var pautaNueveSi:RadioButton? = null; var pautaNueveNo:RadioButton? = null;
var radioGroupNueve:RadioGroup? = null;

var pautaDiezSi:RadioButton? = null; var pautaDiezNo:RadioButton? = null;
var radioGroupDiez:RadioGroup? = null;

var pautaOnceSi:RadioButton? = null; var pautaOnceNo:RadioButton? = null;
var radioGroupOnce:RadioGroup? = null;

var pautaDoceSi:RadioButton? = null; var pautaDoceNo:RadioButton? = null;
var radioGroupDoce:RadioGroup? = null;

var pautaTreceSi:RadioButton? = null; var pautaTreceNo:RadioButton? = null;
var radioGroupTrece:RadioGroup? = null;

var pautaCatorceSi:RadioButton? = null; var pautaCatorceNo:RadioButton? = null;
var radioGroupCatorce:RadioGroup? = null;

var pautaQuinceSi:RadioButton? = null; var pautaQuinceNo:RadioButton? = null;
var radioGroupQuince:RadioGroup? = null;

var pautaDieciseisSi:RadioButton? = null; var pautaDieciseisNo:RadioButton? = null;
var radioGroupDieciseis:RadioGroup? = null;

var pautaDiecisieteSi:RadioButton? = null; var pautaDiecisieteNo:RadioButton? = null;
var radioGroupDiecisiete:RadioGroup? = null;

// RadioButton y RadioGroup para especificar si presenta el cliente los documentos necesarios
var radioGroupDocsNecesarios:RadioGroup? = null;
var docsNecesariosSi:RadioButton? = null;
var docsNecesariosNo:RadioButton? = null;**/
class FormularioFragment : Fragment() {

    lateinit var toggle : ActionBarDrawerToggle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_formulario, container, false)
        // Se inicaliza los RadioButton y RadioGroup de entrada
        /**entradaGroup = view.findViewById(R.id.radioGroupEntrada);
        primeraVez = view.findViewById(R.id.primeraVez);
        segundaVez = view.findViewById(R.id.segundaVez);
        // Se inicaliza los RadioButton y RadioGroup de blindaje si aplica
        blindajeGroup = view.findViewById(R.id.radioGroupBlindaje)
        siBlindado = view.findViewById(R.id.siBlindado)
        noBlindado = view.findViewById(R.id.noBlindado)
        // Se inicaliza los RadioButton y RadioGroup de pautas de ingreso
        radioGroupUno = view.findViewById(R.id.radioGroup1)
        pautaUnoSi = view.findViewById(R.id.pautaunoSiButton)
        pautaUnoNo = view.findViewById(R.id.pautaunoNoButton)

        radioGroupDos = view.findViewById(R.id.radioGroup2)
        pautaDosSi = view.findViewById(R.id.pautadosSiButton)
        pautaDosNo = view.findViewById(R.id.pautadosNoButton)

        radioGroupTres = view.findViewById(R.id.radioGroup3)
        pautaTresSi = view.findViewById(R.id.pautatresSiButton)
        pautaTresNo = view.findViewById(R.id.pautatresNoButton)

        radioGroupCuatro = view.findViewById(R.id.radioGroup4)
        pautaCuatroSi = view.findViewById(R.id.pautacuatroSiButton)
        pautaCuatroNo = view.findViewById(R.id.pautacuatroNoButton)

        radioGroupCinco = view.findViewById(R.id.radioGroup5)
        pautaCincoSi = view.findViewById(R.id.pautacincoSiButton)
        pautaCincoNo = view.findViewById(R.id.pautacincoNoButton)

        radioGroupSeis = view.findViewById(R.id.radioGroup6)
        pautaSeisSi = view.findViewById(R.id.pautaseisSiButton)
        pautaSeisNo = view.findViewById(R.id.pautaseisNoButton)

        radioGroupSeis = view.findViewById(R.id.radioGroup6)
        pautaSeisSi = view.findViewById(R.id.pautaseisSiButton)
        pautaSeisNo = view.findViewById(R.id.pautaseisNoButton)

        radioGroupSiete = view.findViewById(R.id.radioGroup7)
        pautaSieteSi = view.findViewById(R.id.pautasieteSiButton)
        pautaSieteNo = view.findViewById(R.id.pautasieteNoButton)

        radioGroupOcho = view.findViewById(R.id.radioGroup8)
        pautaOchoSi = view.findViewById(R.id.pautaochoSiButton)
        pautaOchoNo = view.findViewById(R.id.pautaochoNoButton)

        radioGroupNueve = view.findViewById(R.id.radioGroup9)
        pautaNueveSi = view.findViewById(R.id.pautanueveSiButton)
        pautaNueveNo = view.findViewById(R.id.pautanueveNoButton)

        radioGroupDiez = view.findViewById(R.id.radioGroup10)
        pautaDiezSi = view.findViewById(R.id.pautadiezSiButton)
        pautaDiezNo = view.findViewById(R.id.pautadiezNoButton)

        radioGroupOnce = view.findViewById(R.id.radioGroup11)
        pautaOnceSi = view.findViewById(R.id.pautaonceSiButton)
        pautaOnceNo = view.findViewById(R.id.pautaonceNoButton)

        radioGroupDoce = view.findViewById(R.id.radioGroup12)
        pautaDoceSi = view.findViewById(R.id.pautadoceSiButton)
        pautaDoceNo = view.findViewById(R.id.pautadoceNoButton)

        radioGroupTrece = view.findViewById(R.id.radioGroup13)
        pautaTreceSi = view.findViewById(R.id.pautatreceSiButton)
        pautaTreceNo = view.findViewById(R.id.pautatreceNoButton)

        radioGroupCatorce = view.findViewById(R.id.radioGroup14)
        pautaCatorceSi = view.findViewById(R.id.pautacatorceSiButton)
        pautaCatorceNo = view.findViewById(R.id.pautacatorceNoButton)

        radioGroupQuince = view.findViewById(R.id.radioGroup15)
        pautaQuinceSi = view.findViewById(R.id.pautaquinceSiButton)
        pautaQuinceNo = view.findViewById(R.id.pautaquinceNoButton)

        radioGroupDieciseis = view.findViewById(R.id.radioGroup16)
        pautaDieciseisSi = view.findViewById(R.id.pautadieciseisSiButton)
        pautaDieciseisNo = view.findViewById(R.id.pautadieciseisNoButton)

        radioGroupDiecisiete = view.findViewById(R.id.radioGroup17)
        pautaDiecisieteSi = view.findViewById(R.id.pautadiecisieteSiButton)
        pautaDiecisieteNo = view.findViewById(R.id.pautadiecisieteNoButton)
        // Se inicializan las variables de RadioGroup y RadioButton para documentos necesarios
        radioGroupDocsNecesarios = view.findViewById(R.id.radioGroupDocsNecesarios)
        docsNecesariosSi = view.findViewById(R.id.docsNecesariosSiButton)
        docsNecesariosNo = view.findViewById(R.id.docsNecesariosNoButton)**/

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

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        super.onViewCreated(view, savedInstanceState)
        val nextFormulario = view.findViewById<Button>(R.id.siguienteButton)
        nextFormulario.setOnClickListener()
        {
            findNavController().navigate(R.id.action_formulario_to_livianoFragment)
        }
    }


}