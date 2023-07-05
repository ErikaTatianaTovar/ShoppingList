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
        return shoppingHomeViewModel.shoppingList?.size ?:0
    }

    override fun onItemDismiss(position: Int) {
        shoppingHomeViewModel.removeNewItemToShop(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

    }

    class ItemShoppingHolder(private val binding: ShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {
        fun setDataShopping(shoppingHomeViewModel: ShoppingHomeViewModel, position: Int) {
            val shoppingList = shoppingHomeViewModel.shoppingList
            val shoppingEntity = shoppingList?.get(position)

            binding.setVariable(BR.shoppingHomeViewModel, shoppingHomeViewModel)
            binding.setVariable(BR.position, position)

            shoppingEntity?.let { entity ->
                binding.textBoxProduct.setText(entity.nameOfProduct)
                binding.textBoxPrice.setText(entity.price.toString())
                binding.textBoxQuantity.setText(entity.quantity.toString())

                binding.textBoxProduct.doOnTextChanged { text, _, _, _ ->
                    val updatedEntity = entity.copy(nameOfProduct = text.toString())
                    shoppingList?.toMutableList()?.set(position, updatedEntity)
                    shoppingHomeViewModel.updateShopping()
                    //falta agregar algo a los parentesis para que actualice ela lista shopping
                }

                binding.textBoxPrice.doOnTextChanged { text, _, _, _ ->
                    val updatedEntity = entity.copy(price = validateIfEmpty(text.toString()).toDouble())
                    shoppingList?.toMutableList()?.set(position, updatedEntity)
                    shoppingHomeViewModel.updateShopping()
                }

                binding.textBoxQuantity.doOnTextChanged { text, _, _, _ ->
                    val updatedEntity = entity.copy(quantity = validateIfEmpty(text.toString()).toInt())
                    shoppingList?.toMutableList()?.set(position, updatedEntity)
                    shoppingHomeViewModel.updateShopping()
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