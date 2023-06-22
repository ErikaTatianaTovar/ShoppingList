package com.example.shoppinglist.application.home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.application.home.view.AdapterCallback
import com.example.shoppinglist.application.home.view.RecyclerShoppingAdapter
import com.example.shoppinglist.domain.models.Shopping
import com.example.shoppinglist.infraestructure.dblocal.AppDatabase
import com.example.shoppinglist.infraestructure.dblocal.dtos.toShoppingEntity
import com.example.shoppinglist.infraestructure.dblocal.repositories.ShoppingRepositoryRoom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingHomeViewModel : ViewModel() {
    val recyclerShoppingAdapter: RecyclerShoppingAdapter = RecyclerShoppingAdapter(this, {""}, null)
    val shoppingList: ArrayList<Shopping> = arrayListOf(Shopping(0, "", 0.0, 0, 0.0))
    private lateinit var shoppingRepositoryRoom: ShoppingRepositoryRoom
    var adapterCallback: AdapterCallback? = null


    fun createDB(context: Context) {
        val shoppingDao = AppDatabase.getInstance(context).shoppingDao()
        shoppingRepositoryRoom = ShoppingRepositoryRoom(shoppingDao)
    }
    fun updateAdapterCallback(callback: AdapterCallback?) {
        adapterCallback = callback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewItemShop() {
        val shopping = Shopping(0, "", 0.0, 0, 0.0)
        shoppingList.add(shopping)
        recyclerShoppingAdapter.notifyDataSetChanged()
        GlobalScope.launch {
            shoppingRepositoryRoom.insertAll(shoppingList.toShoppingEntity())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNewItemToShop(position: Int) {
        shoppingList.removeAt(position)
        recyclerShoppingAdapter.notifyDataSetChanged()
    }
    fun getCalculateTotalPricePerProduct(adapterCallback: AdapterCallback?): LiveData<Double> {
        this.adapterCallback = adapterCallback
        return shoppingRepositoryRoom.getCalculateTotalPricePerProduct()
    }

    fun getSumOfPrices(): LiveData<Double> {
        return shoppingRepositoryRoom.getSumOfPrices()
    }
}
