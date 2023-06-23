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
import com.example.shoppinglist.infraestructure.dblocal.dtos.toShoppingEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ShoppingHomeFragment : Fragment(), AdapterCallback {

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
        val root: View = binding.root
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProvider(this)[ShoppingHomeViewModel::class.java]
        recyclerShoppingAdapter = RecyclerShoppingAdapter(homeViewModel, {""}, this)

        binding.rvShopping.adapter = recyclerShoppingAdapter

        homeViewModel.createDB(requireContext())

        binding.shoppingFloatingActionButton.setOnClickListener {
            homeViewModel.addNewItemShop(recyclerShoppingAdapter)
        }
        itemTouchCallback()
        sumOfPrices()
    }

     override fun onValueUpdated(position: Int, value: String, idShopping: Int) {
        val viewHolder =
            binding.rvShopping.findViewHolderForAdapterPosition(position) as? RecyclerShoppingAdapter.ItemShoppingHolder
        viewHolder?.binding?.valueTotalPerProduct?.text = value

        val calculate = GlobalScope.launch {homeViewModel.getCalculateTotalPricePerProduct(idShopping)
        }
        val addToPrice = getString(R.string.value_total_per_product, (calculate).toString())
        viewHolder?.binding?.valueTotalPerProduct?.text = addToPrice

    }
    private fun itemTouchCallback() {
        val itemTouchHelperCallback = ItemTouchHelperCallback(recyclerShoppingAdapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvShopping)

        binding.model = homeViewModel
    }

    fun sumOfPrices() {
        homeViewModel.getSumOfPrices().observe(viewLifecycleOwner) { calculate ->
            val addToTotalPrice =
                getString(R.string.text_total, (calculate ?: 0.0).toString())
            binding.textTotal.text = addToTotalPrice
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}