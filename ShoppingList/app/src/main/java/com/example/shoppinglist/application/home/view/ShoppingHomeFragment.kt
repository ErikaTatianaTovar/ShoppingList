package com.example.shoppinglist.application.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.application.home.viewmodel.ShoppingHomeViewModel
import com.example.shoppinglist.databinding.FragmentHomeBinding

class ShoppingHomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerViewShopping: RecyclerView
    private lateinit var shoppingAdapter: RecyclerShoppingAdapter
    private lateinit var homeViewModel: ShoppingHomeViewModel
    //FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this)[ShoppingHomeViewModel::class.java]
        shoppingAdapter = RecyclerShoppingAdapter(homeViewModel)

        recyclerViewShopping = binding.recyclerViewShopping

        itemTouchCallback()




    }

    private fun itemTouchCallback() {
        val itemTouchHelperCallback = ItemTouchHelperCallback(shoppingAdapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerViewShopping)

        binding.model = homeViewModel
        binding.floatingActionButton.setOnClickListener {
            homeViewModel.addNewItemShop()
            val songsFound = getString(R.string.text_total, homeViewModel.getSumOfPrices().toString())
            binding.textTotal.text = songsFound
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}