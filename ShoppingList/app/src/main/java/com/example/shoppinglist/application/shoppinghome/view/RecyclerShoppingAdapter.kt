package com.example.shoppinglist.application.shoppinghome.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.application.shoppinghome.viewmodel.ShoppingHomeViewModel
import com.example.shoppinglist.databinding.ShoppingItemBinding
import com.example.shoppinglist.infraestructure.dblocal.dtos.toShoppingEntity

class RecyclerShoppingAdapter(private val shoppingHomeViewModel: ShoppingHomeViewModel) :
    RecyclerView.Adapter<RecyclerShoppingAdapter.ItemShoppingHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShoppingHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ShoppingItemBinding.inflate(layoutInflater, parent, false)
        return ItemShoppingHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemShoppingHolder, position: Int) {
        holder.setDataShopping(shoppingHomeViewModel, position)
    }

    override fun getItemCount(): Int {
        return shoppingHomeViewModel.shoppingList?.size ?: 0
    }

    override fun onItemDismiss(position: Int) {
        shoppingHomeViewModel.removeNewItemToShop(position)
    }

    class ItemShoppingHolder(private val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun setDataShopping(shoppingHomeViewModel: ShoppingHomeViewModel, position: Int) {
            shoppingHomeViewModel.shoppingList?.get(position)?.let { itemShopping ->
                binding.boxNameOfProduct.text = itemShopping.nameOfProduct
                binding.boxPrice.text = (itemShopping.price).formatCurrency()
                binding.boxQuantity.text = itemShopping.quantity.toString()
            }
            binding.root.setOnClickListener {
                shoppingHomeViewModel.shoppingList?.get(position)?.let {
                    val itemShoppingPopup = ItemShoppingPopup(binding.root.context)
                    itemShoppingPopup.showItemShoppingPopup(shoppingEntity = it.toShoppingEntity()) { shoppingEntity ->
                        shoppingHomeViewModel.updateShopping(shoppingEntity)
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