package com.example.noteapp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.state.ColorObject
import com.example.noteapp.utils.OnItemClick
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily

class ColorAdapter(private val list: List<ColorObject>):RecyclerView.Adapter<ColorAdapter.ViewHolder>(){

    private var lastItem:Int? = null
    private var listener: OnItemClick<ColorObject>? = null
    fun setOnItemClickListener(f: OnItemClick<ColorObject>) {
        listener = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view = LayoutInflater.from(parent.context).inflate(R.layout.color_recycler_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.colorRecycler.setCardBackgroundColor(Color.BLUE)

        if (lastItem!=null&&lastItem==position){
            holder.cardColor.elevation = 30f
            holder.cardColor.strokeColor = Color.parseColor(list[position].color)
        } else {
            holder.cardColor.elevation = 0f
            holder.cardColor.strokeColor = Color.GRAY

        }
        holder.apply {
//            cardColor.elevation = 0f
//            cardColor.shapeAppearanceModel = cardColor.shapeAppearanceModel.toBuilder().build()
            cardColor.setCardBackgroundColor(Color.parseColor(list[position].color))
            itemView.setOnClickListener {
                listener?.invoke(list[position])
                lastItem = position
                notifyDataSetChanged()
            }
//                for (i in list){
//                    if (i.colorName==list[position].colorName){
//                        colorRecycler.setCardBackgroundColor(Color.parseColor(i.color))
//                    }
//                }
        }

    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardColor: MaterialCardView = itemView.findViewById<MaterialCardView>(R.id.cardColor)
    }

}