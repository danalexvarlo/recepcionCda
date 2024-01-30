package com.example.recepcioncda.view.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.recepcioncda.R

class EntradaAdapter: RecyclerView.Adapter<EntradaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(ViewGroup: ViewGroup, i: Int): ViewHolder {
        TODO("Not yet implemented")
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

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}