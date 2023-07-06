package com.example.shoppinglist.application.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.BR
import com.example.shoppinglist.R
import com.example.shoppinglist.application.home.viewmodel.ShoppingHomeViewModel
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

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

    }

    class ItemShoppingHolder(private val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {
        fun setDataShopping(shoppingHomeViewModel: ShoppingHomeViewModel, position: Int) {
            binding.setVariable(BR.shoppingHomeViewModel, shoppingHomeViewModel)
            binding.setVariable(BR.position, position)

            shoppingHomeViewModel.shoppingList?.get(position)?.let { itemShopping ->
                binding.boxNameOfProduct.setText(itemShopping.nameOfProduct)
                binding.boxPrice.setText(itemShopping.price.toString())
                binding.boxQuantity.setText(itemShopping.quantity.toString())

                binding.boxNameOfProduct.doOnTextChanged { text, _, _, _ ->
                    if (text != itemShopping.nameOfProduct){
                        val updatedShopping = itemShopping.copy(nameOfProduct = text.toString())
                        shoppingHomeViewModel.updateShopping(updatedShopping.toShoppingEntity())
                    }
                }

                binding.boxPrice.doOnTextChanged { text, _, _, _ ->
                    if (text != itemShopping.price.toString()) {
                        val updatedShopping =
                            itemShopping.copy(price = validateIfEmpty(text.toString()).toDouble())
                        shoppingHomeViewModel.updateShopping(updatedShopping.toShoppingEntity())
                    }
                }

                binding.boxQuantity.doOnTextChanged { text, _, _, _ ->
                    if (text != itemShopping.quantity.toString()) {
                        val updatedShopping =
                            itemShopping.copy(quantity = validateIfEmpty(text.toString()).toInt())
                        shoppingHomeViewModel.updateShopping(updatedShopping.toShoppingEntity())
                    }
                }
            }

            binding.executePendingBindings()
        }

        private fun validateIfEmpty(value: String): String {
            if (value.isEmpty()) return "0"
            return value
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
                    R.color.white
                )
            )
        }
    }
}