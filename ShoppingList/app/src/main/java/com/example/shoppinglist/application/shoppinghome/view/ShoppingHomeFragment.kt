package com.example.shoppinglist.application.shoppinghome.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.shoppinglist.MainActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.application.shoppinghome.viewmodel.ShoppingHomeViewModel
import com.example.shoppinglist.databinding.FragmentHomeBinding
import com.example.shoppinglist.infraestructure.dblocal.dtos.toDomainModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

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
    }

    private fun allShopping() {
        homeViewModel.getAllShopping().observe(viewLifecycleOwner) { shoppingList ->
            homeViewModel.shoppingList = shoppingList.toDomainModel()
            recyclerShoppingAdapter.notifyDataSetChanged()
            (activity as MainActivity).hideLoading()
        }
    }

    private fun showNewItemShoppingPopup() {
        val itemShoppingPopup = ItemShoppingPopup(requireContext())
        itemShoppingPopup.showItemShoppingPopup {
            homeViewModel.addNewItemShop(it)
        }
    }

    private fun sumOfPrices() {
        homeViewModel.getSumOfPrices().observe(viewLifecycleOwner) { sum ->
            val songsFound = getString(R.string.text_total, (sum ?: 0.0).formatCurrency())
            binding.textTotal.text = songsFound
            recyclerShoppingAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

    fun Double.formatCurrency(): String {
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
        return currencyFormat.format(this)
}