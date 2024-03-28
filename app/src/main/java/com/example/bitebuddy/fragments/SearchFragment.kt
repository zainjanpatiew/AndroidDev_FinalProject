package com.example.bitebuddy.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitebuddy.R
import com.example.bitebuddy.activities.MainActivity
import com.example.bitebuddy.adapters.CategoriesAdapter
import com.example.bitebuddy.adapters.MealsAdapter
import com.example.bitebuddy.databinding.FragmentSearchBinding
import com.example.bitebuddy.videoModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerviewAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        binding.imgSearchArrow.setOnClickListener{ searchMeals() }

        observeSearchedMealsLiveData()

        var searchJob:Job? = null
        binding.edSearchBox.addTextChangedListener {searchQuery ->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(300)
                viewModel.searchMeals(searchQuery.toString())
            }

        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchMealsLiveData().observe(viewLifecycleOwner) { mealsList ->

            searchRecyclerviewAdapter.differ.submitList(mealsList)
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if(searchQuery.isNotEmpty()){
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerviewAdapter = MealsAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerviewAdapter
        }
    }
}