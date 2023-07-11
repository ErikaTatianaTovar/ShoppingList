package com.example.shoppinglist.application.marketlist.view

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemMarketPopupBinding
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import java.lang.NumberFormatException

class ItemMarketPopup ( private val context: Context) {

    fun showItemMarketPopup(
        marketEntity: MarketEntity? = null,
        action: (MarketEntity) -> Unit
    ) {
        val inflater = LayoutInflater.from(context)
        val binding: ItemMarketPopupBinding = ItemMarketPopupBinding.inflate(inflater)
        val popupView = binding.root

        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        marketEntity?.let {
            binding.boxNameOfProduct.setText(it.nameOfProduct)
            binding.boxQuantity.setText(it.quantity)
        }

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true

        binding.applyNewItemButton.setOnClickListener {
            val name = binding.boxNameOfProduct.text.toString()
            val quantity = binding.boxQuantity.text.toString()

            if (name.isNotEmpty() && quantity.isNotEmpty()) {
                try {
                    action.invoke(
                        MarketEntity(
                            id = marketEntity?.id ?: 0,
                            nameOfProduct = name,
                            quantity = quantity
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