package com.example.shoppinglist.application.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.application.home.model.ShoppingObservable
import com.example.shoppinglist.application.home.view.RecyclerShoppingAdapter
import com.example.shoppinglist.databinding.ShoppingItemBinding
import com.example.shoppinglist.domain.models.Shopping

class ShoppingHomeViewModel : ViewModel() {

    val recyclerShoppingAdapter: RecyclerShoppingAdapter = RecyclerShoppingAdapter(this)
    val shoppingList: ArrayList<Shopping> = arrayListOf(Shopping(0, "", 0, 0))

    fun addNewItemShop() {
        val shopping = Shopping(0, "", 0, 0)
        shoppingList.add(shopping)
        recyclerShoppingAdapter.notifyDataSetChanged()
    }

    fun removeNewItemToShop() {
        shoppingList.removeAt(0)
        recyclerShoppingAdapter.notifyDataSetChanged()
    }
}