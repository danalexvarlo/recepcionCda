package com.example.recepcioncda.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.recepcioncda.R

class EntradaAdapter: RecyclerView.Adapter<EntradaAdapter.ViewHolder>()
{
    lateinit var nombre : EditText
    override fun onCreateViewHolder(ViewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.card_view_entrada,
            ViewGroup, false)
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var itemNombre: EditText
        var itemTipodocumento: EditText
        var itemDocumento: EditText
        var itemDireccion: EditText
        var itemTelefono: EditText
        var itemCorreo: EditText

        init {
            itemNombre = itemView.findViewById(R.id.editNombreConductor)
            itemTipodocumento = itemView.findViewById(R.id.editTipodoctConductor)
            itemDocumento = itemView.findViewById(R.id.editDocumentoConductor)
            itemDireccion = itemView.findViewById(R.id.editDireccionConductor)
            itemTelefono = itemView.findViewById(R.id.editTelefonoConductor)
            itemCorreo = itemView.findViewById(R.id.editCorreoConductor)
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int)
    {
        viewHolder.itemNombre.editableText
        viewHolder.itemTipodocumento.editableText
        viewHolder.itemDocumento.editableText
        viewHolder.itemDireccion.editableText
        viewHolder.itemTelefono.editableText
        viewHolder.itemCorreo.editableText
    }

    override fun getItemCount(): Int
    {
        return editNombreConductor.size
    }
}