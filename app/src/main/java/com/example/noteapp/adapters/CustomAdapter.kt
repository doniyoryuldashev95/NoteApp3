package com.example.noteapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.room.WordEntity
import com.example.noteapp.state.ColorObject
import com.example.noteapp.utils.OnItemClick
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily

class CustomAdapter(private var list: List<WordEntity>):RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    private var listener: OnItemClick<WordEntity>? = null
    fun setOnItemClickListener(f: OnItemClick<WordEntity>) {
        listener = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view = LayoutInflater.from(parent.context).inflate(R.layout.user_recycler_item,parent,false)
        return ViewHolder(view)
    }
    fun setFilterList(filterList: List<WordEntity>){
        list = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].title
        holder.surname.text = list[position].description
        holder.leaf.shapeAppearanceModel = holder.leaf.shapeAppearanceModel.toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED,0f)
            .setTopRightCorner(CornerFamily.ROUNDED,0f).build()
        holder.leaf.setCardBackgroundColor(Color.parseColor("#ebf6f5"))
        holder.leaf.strokeColor = Color.BLACK
        holder.leaf.elevation = 3f

        holder.itemView.setOnClickListener {
            listener?.invoke(list[position])
        }

    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.title)
        val surname: TextView = itemView.findViewById(R.id.description)
        val leaf = itemView.findViewById<MaterialCardView>(R.id.leaf)
    }

}