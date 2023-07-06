package com.example.shoppinglist.application.home.view

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.shoppinglist.R
import com.example.shoppinglist.application.home.viewmodel.ShoppingHomeViewModel
import com.example.shoppinglist.databinding.FragmentHomeBinding
import com.example.shoppinglist.databinding.ItemPopupBinding
import com.example.shoppinglist.infraestructure.dblocal.dtos.toDomainModel
import dagger.hilt.android.AndroidEntryPoint
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
            showNewItemShoppingPopup()
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

    private fun showNewItemShoppingPopup() {
        val binding: ItemPopupBinding = ItemPopupBinding.inflate(layoutInflater)
        val popupView = binding.root

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        binding.applyNewItemButton.setOnClickListener {
            val name = binding.boxNameOfProduct.text.toString()
            val price = binding.boxPrice.text.toString().toDouble()
            val quantity = binding.boxQuantity.text.toString().toInt()
            if (name.isNotEmpty() && price != null && quantity != null) {
                homeViewModel.addNewItemShop(name, price, quantity)
                popupWindow.dismiss()
            } else {
                Toast.makeText(requireContext(), R.string.Please_fill_all_fields_correctly, Toast.LENGTH_SHORT).show()
            }
        }

        popupWindow.showAtLocation(binding.root, Gravity.AXIS_CLIP, 0, 0)
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