package com.example.bitebuddy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitebuddy.databinding.PopularItemsBinding
import com.example.bitebuddy.pojo.MealsByCategory

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    var onLongItemClick:((MealsByCategory) -> Unit)? = null
    private var mealslist = ArrayList<MealsByCategory>()

    fun setMeals(mealsList:ArrayList<MealsByCategory>){
        this.mealslist = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealslist[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealslist[position])
        }

        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealslist[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return mealslist.size
    }

    class PopularMealViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}