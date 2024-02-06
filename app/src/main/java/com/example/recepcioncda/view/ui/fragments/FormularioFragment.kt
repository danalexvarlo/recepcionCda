package com.example.recepcioncda.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recepcioncda.R
import com.example.recepcioncda.view.adapter.formularioAdapter

class formulario : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var recyclerLib: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_formulario, container, false)

        recyclerLib = view.findViewById(R.id.recyclerView)
        val adapter = formularioAdapter()
        recyclerLib.layoutManager = LinearLayoutManager(context)
        recyclerLib.adapter = adapter

        return view
    }
}