package com.example.shoppinglist.application.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.BR
import com.example.shoppinglist.R
import com.example.shoppinglist.application.home.viewmodel.ShoppingHomeViewModel
import com.example.shoppinglist.databinding.ShoppingItemBinding

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
        return shoppingHomeViewModel.shoppingList.size
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

            binding.textBoxProduct.setText(shoppingHomeViewModel.shoppingList[position].nameOfProduct)
            binding.textBoxPrice.setText(shoppingHomeViewModel.shoppingList[position].price.toString())
            binding.textBoxQuantity.setText(shoppingHomeViewModel.shoppingList[position].quantity.toString())

            binding.textBoxProduct.doOnTextChanged { text, _, _, _ ->
                shoppingHomeViewModel.shoppingList[position].nameOfProduct = text.toString()
            }

            binding.textBoxPrice.doOnTextChanged { text, _, _, _ ->
                shoppingHomeViewModel.shoppingList[position].price = validateIfEmpty(text.toString()).toDouble()
            }

            binding.textBoxQuantity.doOnTextChanged { text, _, _, _ ->
                shoppingHomeViewModel.shoppingList[position].quantity = validateIfEmpty(text.toString()).toInt()
            }

            binding.executePendingBindings()
        }

        private fun validateIfEmpty(value: String): String{
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