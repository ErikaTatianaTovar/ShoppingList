package com.example.shoppinglist.application.marketlist.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.BR
import com.example.shoppinglist.R
import com.example.shoppinglist.application.shoppinghome.view.ItemTouchHelperAdapter
import com.example.shoppinglist.application.shoppinghome.view.ItemTouchHelperViewHolder
import com.example.shoppinglist.application.marketlist.viewmodel.MarketListViewModel
import com.example.shoppinglist.databinding.MarketItemBinding

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
        return marketListViewModel.marketList.size
    }

    override fun onItemDismiss(position: Int) {
        marketListViewModel.removeNewItemToMarketList(position)
    }

    class ItemMarketHolder(private val binding: MarketItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun setDataMarket(marketListViewModel: MarketListViewModel, position: Int) {
            val market = marketListViewModel.marketList[position]

            binding.setVariable(BR.marketListViewModel, marketListViewModel)
            binding.setVariable(BR.position, position)

            binding.textProduct.doOnTextChanged { text, _, _, _ ->
                marketListViewModel.marketList[position].nameOfProduct = text.toString()
                Log.d(
                    "RecyclerMarketListAdapter",
                    "Updated nameOfProduct: ${marketListViewModel.marketList[position].nameOfProduct}"
                )
            }

            binding.textQuantity.doOnTextChanged { text, _, _, _ ->
                val quantity = text.toString().toIntOrNull()
                marketListViewModel.marketList[position].quantity = quantity ?: 0
                Log.d(
                    "RecyclerMarketListAdapter",
                    "Updated quantity: ${marketListViewModel.marketList[position].quantity}"
                )
            }

            binding.executePendingBindings()
        }

        override fun onItemSelected() {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )
        }
    }
}