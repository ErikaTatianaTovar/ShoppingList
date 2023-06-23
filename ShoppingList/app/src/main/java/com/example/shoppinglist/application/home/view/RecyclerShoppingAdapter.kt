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

class RecyclerShoppingAdapter(
    private val shoppingHomeViewModel: ShoppingHomeViewModel,
    private val getValueByQuantity: () -> String,
    private var adapterCallback: AdapterCallback? = null
) :
    RecyclerView.Adapter<RecyclerShoppingAdapter.ItemShoppingHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemShoppingHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ShoppingItemBinding.inflate(layoutInflater, parent, false)
        return ItemShoppingHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemShoppingHolder, position: Int) {
        holder.setDataShopping(shoppingHomeViewModel, position, getValueByQuantity, adapterCallback)
    }

    override fun getItemCount(): Int {
        return shoppingHomeViewModel.shoppingList.size
    }

    override fun onItemDismiss(position: Int) {
        shoppingHomeViewModel.removeNewItemToShop(position, this)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {

    }

    class ItemShoppingHolder(
        val binding: ShoppingItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        fun setDataShopping(
            shoppingHomeViewModel: ShoppingHomeViewModel,
            position: Int,
            getValueByQuantity: () -> String,
            adapterCallback: AdapterCallback?
        ) {
            val shopping = shoppingHomeViewModel.shoppingList[position]

            binding.setVariable(BR.shoppingHomeViewModel, shoppingHomeViewModel)
            binding.setVariable(BR.position, position)

            binding.textBoxProduct.doOnTextChanged { text, _, _, _ ->
                shopping.nameOfProduct = text.toString()
            }

            binding.textBoxUnitPrice.doOnTextChanged { text, _, _, _ ->
                shopping.unitPrice = text.toString().toDouble()
                binding.valueTotalPerProduct.text = getValueByQuantity()
                adapterCallback?.onValueUpdated(adapterPosition, getValueByQuantity(),shopping.id)
            }

            binding.textBoxQuantity.doOnTextChanged { text, _, _, _ ->
                shopping.quantity = text.toString().toInt()
                binding.valueTotalPerProduct.text = getValueByQuantity()
                adapterCallback?.onValueUpdated(adapterPosition, getValueByQuantity(),shopping.id)
            }

            binding.executePendingBindings()
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