package com.misha.a7minutesapp.addapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.misha.a7minutesapp.R
import com.misha.a7minutesapp.databinding.ItemExerciseStatusBinding
import com.misha.a7minutesapp.model.ExercisesModel

class ExerciseAdapter(private val list:ArrayList<ExercisesModel>):
    RecyclerView.Adapter<ExerciseAdapter.ExercisesHolder>() {
    class ExercisesHolder(binding: ItemExerciseStatusBinding) :RecyclerView.ViewHolder(
        binding.root){
        val tvItem = binding.tvItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercisesHolder {
        return ExercisesHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ExercisesHolder, position: Int) {
        val model:ExercisesModel = list[position]
        holder.tvItem.text = model.id.toString()

        when {
            model.isCompleted -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_green_bg)
                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            model.isSelected -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_white_with_green_stroke_bg)
            }
            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_circular_color_gray_bg)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}