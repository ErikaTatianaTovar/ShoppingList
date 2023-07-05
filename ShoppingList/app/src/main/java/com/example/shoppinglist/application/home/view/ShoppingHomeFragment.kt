package com.example.shoppinglist.application.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.application.home.viewmodel.ShoppingHomeViewModel
import com.example.shoppinglist.databinding.FragmentHomeBinding
import com.example.shoppinglist.infraestructure.dblocal.dtos.toDomainModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Delay

@AndroidEntryPoint
class ShoppingHomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: ShoppingHomeViewModel
    private lateinit var recyclerShoppingAdapter: RecyclerShoppingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this)[ShoppingHomeViewModel::class.java]

        Thread.sleep(5000) // 2000 milisegundos (2 segundos)

        recyclerShoppingAdapter = RecyclerShoppingAdapter(homeViewModel)
        binding.rvShopping.adapter = recyclerShoppingAdapter
        binding.shoppingFloatingActionButton.setOnClickListener {
            homeViewModel.addNewItemShop()
        }
        itemTouchCallback()
        allShopping()
        sumOfPrices()
    }

    private fun itemTouchCallback() {
        val itemTouchHelperCallback = ItemTouchHelperCallback(recyclerShoppingAdapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvShopping)
        binding.model = homeViewModel
    }

    private fun allShopping() {
        homeViewModel.getAllShopping().observe(viewLifecycleOwner) { shoppingList ->
            homeViewModel.shoppingList = shoppingList.toDomainModel()
            recyclerShoppingAdapter.notifyDataSetChanged()
        }
    }

    private fun sumOfPrices() {
        homeViewModel.getSumOfPrices().observe(viewLifecycleOwner) { sum ->
            val songsFound = getString(R.string.text_total, (sum ?: 0.0).toString())
            binding.textTotal.text = songsFound
            recyclerShoppingAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}