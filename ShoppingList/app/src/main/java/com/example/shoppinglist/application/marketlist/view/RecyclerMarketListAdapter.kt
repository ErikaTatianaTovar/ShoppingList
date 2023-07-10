package com.example.shoppinglist.application.marketlist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.application.shoppinghome.view.ItemTouchHelperAdapter
import com.example.shoppinglist.application.shoppinghome.view.ItemTouchHelperViewHolder
import com.example.shoppinglist.application.marketlist.viewmodel.MarketListViewModel
import com.example.shoppinglist.application.shoppinghome.view.ItemMarketPopup
import com.example.shoppinglist.databinding.MarketItemBinding
import com.example.shoppinglist.infraestructure.dblocal.dtos.toMarketEntity

class RecyclerMarketListAdapter(private val marketListViewModel: MarketListViewModel) :
    RecyclerView.Adapter<RecyclerMarketListAdapter.ItemMarketHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMarketHolder {

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = MarketItemBinding.inflate(layoutInflater, parent, false)
        return ItemMarketHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMarketHolder, position: Int) {
        holder.setDataMarket(marketListViewModel, position)
    }

    override fun getItemCount(): Int {
        return marketListViewModel.marketList?.size ?: 0
    }

    override fun onItemDismiss(position: Int) {
        marketListViewModel.removeNewItemToMarketList(position)
    }

    class ItemMarketHolder(private val binding: MarketItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun setDataMarket(marketListViewModel: MarketListViewModel, position: Int) {
            marketListViewModel.marketList?.get(position)?.let { itemMarket ->
                binding.boxNameOfProduct.text = itemMarket.nameOfProduct
                binding.boxQuantity.text = itemMarket.quantity.toString()
            }
            binding.root.setOnClickListener {
                marketListViewModel.marketList?.get(position)?.let {
                    val itemMarketPopup = ItemMarketPopup(binding.root.context)
                    itemMarketPopup.showItemMarketPopup(marketEntity = it.toMarketEntity()) { marketEntity ->
                        marketListViewModel.updateMarket(marketEntity)
                    }
                }
            }
        }
        override fun onItemSelected() {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.red
                )
            )
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    com.google.android.material.R.color.mtrl_btn_transparent_bg_color
                )
            )
        }
    }
}