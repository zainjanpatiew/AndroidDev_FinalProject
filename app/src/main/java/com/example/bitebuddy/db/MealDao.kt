package com.example.bitebuddy.db

import androidx.room.*
import com.example.bitebuddy.pojo.Meal
import androidx.lifecycle.LiveData

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}