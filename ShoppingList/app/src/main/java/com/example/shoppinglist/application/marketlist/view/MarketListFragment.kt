package com.example.shoppinglist.application.marketlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.shoppinglist.MainActivity
import com.example.shoppinglist.application.shared.ItemTouchHelperCallback
import com.example.shoppinglist.application.marketlist.viewmodel.MarketListViewModel
import com.example.shoppinglist.databinding.FragmentMarketListBinding
import com.example.shoppinglist.infraestructure.dblocal.dtos.toDomainModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarketListFragment : Fragment() {

    private var _binding: FragmentMarketListBinding? = null
    private val binding get() = _binding!!
    private lateinit var marketListViewModel: MarketListViewModel
    private lateinit var rvMarketListAdapter: RecyclerMarketListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMarketListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marketListViewModel = ViewModelProvider(this)[MarketListViewModel::class.java]

        rvMarketListAdapter = RecyclerMarketListAdapter(marketListViewModel)
        binding.rvMarketList.adapter = rvMarketListAdapter

        binding.marketFloatingActionButton.setOnClickListener {
            showNewItemMarketPopup()
        }
        itemTouchCallback()
        allMarket()
    }

    private fun itemTouchCallback() {
        val itemTouchHelperCallback = ItemTouchHelperCallback(rvMarketListAdapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvMarketList)
    }

    private fun allMarket() {
        marketListViewModel.getAllMarket().observe(viewLifecycleOwner) { marketList ->
            marketListViewModel.marketList = marketList.toDomainModel()
            rvMarketListAdapter.notifyDataSetChanged()
            (activity as MainActivity).hideLoading()
        }
    }

    private fun showNewItemMarketPopup() {
        val itemMarketPopup = ItemMarketPopup(requireContext())
        itemMarketPopup.showItemMarketPopup {
            marketListViewModel.addNewItemMarketList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}