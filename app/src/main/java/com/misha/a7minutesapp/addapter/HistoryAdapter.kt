package com.misha.a7minutesapp.addapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.misha.a7minutesapp.R
import com.misha.a7minutesapp.database.history.HistoryEntity
import com.misha.a7minutesapp.databinding.ItemHistoryBinding

class HistoryAdapter(private val list:ArrayList<HistoryEntity>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(binding: ItemHistoryBinding):RecyclerView.ViewHolder(binding.root){
        val layout = binding.llHistoryItemMain
        val pos = binding.tvPosition
        val date = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pos.text = (list.size - position).toString()
        holder.date.text = list[position].date
        if (position % 2 == 0)
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        else
            holder.layout.setBackgroundColor(Color.parseColor("#EBEBEB"))

    }

    override fun getItemCount(): Int {
       return list.size
    }
}