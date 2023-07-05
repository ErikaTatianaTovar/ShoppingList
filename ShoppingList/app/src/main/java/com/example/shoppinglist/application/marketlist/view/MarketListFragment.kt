package com.example.shoppinglist.application.marketlist.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.application.home.view.ItemTouchHelperCallback
import com.example.shoppinglist.application.marketlist.viewmodel.MarketListViewModel
import com.example.shoppinglist.databinding.FragmentMarketListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketListFragment : Fragment() {

    private var _binding: FragmentMarketListBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvMarket: RecyclerView
    private lateinit var marketListViewModel: MarketListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMarketListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marketListViewModel = ViewModelProvider(this)[MarketListViewModel::class.java]

        rvMarket = binding.rvMarketList
        rvMarket.adapter = marketListViewModel.recyclerMarketListAdapter


        binding.marketFloatingActionButton.setOnClickListener {
            marketListViewModel.addNewItemMarketList()
        }
        itemTouchCallback()
    }

    private fun itemTouchCallback() {
        try {

            val itemTouchHelperCallback =
                ItemTouchHelperCallback(marketListViewModel.recyclerMarketListAdapter)
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(rvMarket)

            binding.marketListViewModel = marketListViewModel
        } catch (e: Exception) {
            onError(e)
        }
    }

    private fun onError(exception: Exception) {
        val errorMessage = "Se produjo un error: ${exception.message}"
        Log.e("MarketListFragment", errorMessage)

        val logErrorMessage = Log.getStackTraceString(exception)
        val dialogTitle = "Error"
        val dialogMessage = "Ocurrió un error. Detalles del error:\n\n$logErrorMessage"

        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(dialogTitle)
            .setMessage(dialogMessage)
            .setPositiveButton("Aceptar", null)
            .create()

        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}