package com.example.shoppinglist.application.home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.application.home.view.RecyclerShoppingAdapter
import com.example.shoppinglist.domain.models.Shopping
import com.example.shoppinglist.infraestructure.dblocal.AppDataBase
import com.example.shoppinglist.infraestructure.dblocal.dtos.toShoppingEntity
import com.example.shoppinglist.infraestructure.dblocal.repositories.ShoppingRepositoryRoom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingHomeViewModel : ViewModel() {

    val shoppingList: ArrayList<Shopping> = arrayListOf(Shopping(0, "", 0.0, 0, 0.0))
    private lateinit var shoppingRepositoryRoom: ShoppingRepositoryRoom

    fun createDB(context: Context) {
        val shoppingDao = AppDataBase.getInstance(context).shoppingDao()
        shoppingRepositoryRoom = ShoppingRepositoryRoom(shoppingDao)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addNewItemShop(recyclerShoppingAdapter: RecyclerShoppingAdapter) {
        val shopping = Shopping(0, "", 0.0, 0, 0.0)
        shoppingList.add(shopping)
        recyclerShoppingAdapter.notifyDataSetChanged()
        GlobalScope.launch {
            shoppingRepositoryRoom.insertAll(shoppingList.toShoppingEntity())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNewItemToShop(position: Int, recyclerShoppingAdapter: RecyclerShoppingAdapter) {
        shoppingList.removeAt(position)
        recyclerShoppingAdapter.notifyDataSetChanged()
    }
   suspend fun getCalculateTotalPricePerProduct(idShopping: Int){
        return shoppingRepositoryRoom.getCalculateTotalPricePerProduct(idShopping)
    }

    fun getSumOfPrices(): LiveData<Double> {
        return shoppingRepositoryRoom.getSumOfPrices()
    }
}
