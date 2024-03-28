package com.example.bitebuddy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bitebuddy.databinding.MealItemBinding
import com.example.bitebuddy.pojo.Category
import com.example.bitebuddy.pojo.Meal

class MealsAdapter : RecyclerView.Adapter<MealsAdapter.FavoritesMealsAdapterViewHolder>() {
    private lateinit var onItemClick: OnItemCategoryClicked
    private var categoryList:List<Category> = ArrayList()

    inner class FavoritesMealsAdapterViewHolder(val binding:MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    fun onItemClicked(onItemClick: OnItemCategoryClicked){
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealsAdapterViewHolder {
        return FavoritesMealsAdapterViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal
        holder.itemView.setOnClickListener {
            onItemClick.onClickListener(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    interface OnItemCategoryClicked{
        fun onClickListener(category:Category)
    }
}
