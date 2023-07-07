package com.example.shoppinglist.application.shoppinghome.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemPopupBinding
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import java.lang.NumberFormatException

class ItemShoppingPopup(
    private val context: Context
) {

    fun showItemShoppingPopup(
        shoppingEntity: ShoppingEntity? = null,
        action: (ShoppingEntity) -> Unit
    ) {
        val inflater = LayoutInflater.from(context)
        val binding: ItemPopupBinding = ItemPopupBinding.inflate(inflater)
        val popupView = binding.root

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        shoppingEntity?.let {
            binding.boxNameOfProduct.setText(it.nameOfProduct)
            binding.boxPrice.setText(it.price.formatCurrency())
            binding.boxQuantity.setText(it.quantity.toString())
        }

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        binding.applyNewItemButton.setOnClickListener {
            val name = binding.boxNameOfProduct.text.toString()
            val price = binding.boxPrice.text.toString()
            val quantity = binding.boxQuantity.text.toString()

            if (name.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty()) {
                try {
                    action.invoke(
                        ShoppingEntity(
                            id = shoppingEntity?.id ?: 0,
                            nameOfProduct = name,
                            price = price.toDouble(),
                            quantity = quantity.toInt()
                        )
                    )
                } catch (e: NumberFormatException) {
                    Toast.makeText(
                        context,
                        R.string.the_product_entered_is_not_valid,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                popupWindow.dismiss()
            } else {
                Toast.makeText(
                    context,
                    R.string.please_fill_all_fields,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
    }
}