package com.example.recepcioncda.view.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.recepcioncda.R

/**
 * A simple [Fragment] subclass.
 * Use the [formulario.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        return view
    }

}