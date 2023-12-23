package com.example.noteapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.UserRecyclerItemBinding
import com.example.noteapp.room.WordEntity

class WordAdapter(private val list: List<WordEntity>):RecyclerView.Adapter<WordAdapter.Vh>(){

    inner class Vh(private var userRecyclerItemBinding: UserRecyclerItemBinding):RecyclerView.ViewHolder(userRecyclerItemBinding.root){
        fun onBind( userEntity: WordEntity,position: Int){

            userRecyclerItemBinding.apply {
                title.text = userEntity.title
                description.text = userEntity.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
            return Vh(UserRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position],position)
    }

    override fun getItemCount(): Int = list.size

}