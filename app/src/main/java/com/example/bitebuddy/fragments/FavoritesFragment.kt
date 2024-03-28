package com.example.bitebuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bitebuddy.activities.MainActivity
import com.example.bitebuddy.adapters.MealsAdapter
import com.example.bitebuddy.databinding.FragmentFavoritesBinding
import com.example.bitebuddy.videoModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoritesAdapter:MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorite()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoritesAdapter.differ.currentList.getOrNull(position)

                if (deletedMeal != null) {
                    viewModel.deleteMeal(deletedMeal)

                    Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo"
                    ) {
                        viewModel.insertMeal(deletedMeal)
                    }.show()
                } else {
                    Toast.makeText(requireContext(), "Meal not found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

    }

    private fun prepareRecyclerView() {
        favoritesAdapter = MealsAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorite() {
        viewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner) { meals ->
            favoritesAdapter.differ.submitList(meals)

        }
    }


}

