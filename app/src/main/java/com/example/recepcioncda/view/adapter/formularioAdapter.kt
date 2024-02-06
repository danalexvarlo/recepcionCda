package com.example.recepcioncda.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recepcioncda.R

class formularioAdapter: RecyclerView.Adapter<formularioAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(ViewGroup: ViewGroup, i: Int): ViewHolder
    {
        if(i==0)
        {
            val v = LayoutInflater.from(ViewGroup.context).inflate(
                R.layout.card_view_entrada, ViewGroup,
                false)
            return ViewHolder(v)
        }
        else
        {
            val v = LayoutInflater.from(ViewGroup.context).inflate(R.layout.card_view_entrada,
                ViewGroup, false)
            return ViewHolder(v)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }
    override fun getItemViewType(position: Int): Int {
        if(position == 0)
            return 1
        return position % 3
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int)
    {

    }
    override fun getItemCount(): Int
    {
        return 10
    }
}