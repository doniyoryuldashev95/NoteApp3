package com.example.noteapp.adapters

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.room.WordEntity
import com.example.noteapp.utils.OnItemClick

class QuizAdapter(private val list: List<WordEntity>):RecyclerView.Adapter<QuizAdapter.ViewHolder>(){

    private var listener: OnItemClick<WordEntity>? = null
    fun setOnItemClickListener(f: OnItemClick<WordEntity>) {
        listener = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_quiz_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                holder.countDownTimer?.text = "seconds remaining: " + millisUntilFinished / 1000

            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                holder.countDownTimer?.text = "done!"
                holder.textView.text = list[position].title
//                holder.textView.text = list[position].title
            }
        }.start()
//        holder.apply {
////            cardColor.setCardBackgroundColor(Color.parseColor(list[position].color))
////            itemView.setOnClickListener {
////                listener?.invoke(list[position])
////                notifyDataSetChanged()
////            }
//        }

    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView:TextView = itemView.findViewById(R.id.answer_txt)
        var countDownTimer:TextView? = itemView.findViewById(R.id.timer)
    }

}