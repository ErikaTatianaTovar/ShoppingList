package com.example.shoppinglist.application.home.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.application.home.view.RecyclerShoppingAdapter
import com.example.shoppinglist.domain.models.Shopping
import com.example.shoppinglist.infraestructure.dblocal.AppDataBase
import com.example.shoppinglist.infraestructure.dblocal.dtos.toShoppingEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.infraestructure.dblocal.repositories.ShoppingRepositoryRoom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingHomeViewModel : ViewModel() {

    val recyclerShoppingAdapter: RecyclerShoppingAdapter = RecyclerShoppingAdapter(this)
    var shoppingList: ArrayList<Shopping> = arrayListOf(Shopping(0, "", 0.0, 0))
    private lateinit var shoppingRepositoryRoom: ShoppingRepositoryRoom

    fun createDB(context: Context) {
        val shoppingDao = AppDataBase.getInstance(context).shoppingDao()
        shoppingRepositoryRoom = ShoppingRepositoryRoom(shoppingDao)
    }

    fun addNewItemShop() {
        val newItemShopping = Shopping(0, "", 0.0, 0)
        shoppingList.add(newItemShopping)
        recyclerShoppingAdapter.notifyDataSetChanged()
        GlobalScope.launch {
            shoppingRepositoryRoom.insertShopping(shoppingList.toShoppingEntity())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNewItemToShop(position: Int) {
        val itemToRemove = shoppingList[position]
        val idToRemove = itemToRemove.id.toInt()
        viewModelScope.launch {
            shoppingRepositoryRoom.deleteItemById(idToRemove)
        }
        shoppingList.removeAt(position)
        recyclerShoppingAdapter.notifyDataSetChanged()
    }

    fun getSumOfPrices(): LiveData<Double> {
        return shoppingRepositoryRoom.getSumOfPrices()
    }

    fun getAllShopping(): LiveData<List<ShoppingEntity>> {
        return shoppingRepositoryRoom.getAllShopping()
    }
}