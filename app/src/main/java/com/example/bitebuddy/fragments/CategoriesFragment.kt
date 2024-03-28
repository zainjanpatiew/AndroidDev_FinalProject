package com.example.bitebuddy.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bitebuddy.R
import com.example.bitebuddy.activities.CategoryMealsActivity
import com.example.bitebuddy.activities.MainActivity
import com.example.bitebuddy.activities.MealActivity
import com.example.bitebuddy.adapters.CategoriesAdapter
import com.example.bitebuddy.databinding.FragmentCategoriesBinding
import com.example.bitebuddy.fragments.HomeFragment.Companion.CATEGORY_NAME
import com.example.bitebuddy.pojo.Category
import com.example.bitebuddy.videoModel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding:FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
//        onCategoryClick()
        observeCategories()
    }

//    private fun onCategoryClick() {
//        categoriesAdapter.onItemClicked(object : CategoriesAdapter.OnItemCategoryClicked{
//            override fun onClickListener(category: Category) {
//                val intent = Intent(context, MealActivity::class.java)
//                intent.putExtra(CATEGORY_NAME,category.strCategory)
//                startActivity(intent)
//            }
//        })
//    }

    private fun observeCategories() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
        }

    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoriesAdapter().apply {
            onItemClick = { category ->
                // Handle the click here, for example, start an activity with the category name
                val intent = Intent(activity, CategoryMealsActivity::class.java)
                intent.putExtra(CATEGORY_NAME, category.strCategory)
                startActivity(intent)
            }
        }

        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }


}