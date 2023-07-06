package com.example.shoppinglist.application.home.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.models.Shopping
import com.example.shoppinglist.infraestructure.dblocal.dtos.toShoppingEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.infraestructure.dblocal.repositories.ShoppingRepositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShoppingHomeViewModel @Inject constructor(private val shoppingRepositoryRoom: ShoppingRepositoryRoom) :
    ViewModel() {

    var shoppingList: List<Shopping>? = null
    fun getAllShopping() = shoppingRepositoryRoom.getAllShopping()

    fun addNewItemShop(name: String, price: Double, quantity: Int) {
        val shoppingEntity = ShoppingEntity(nameOfProduct = name, price = price, quantity = quantity)
        viewModelScope.launch {
            shoppingRepositoryRoom.insertShopping(shoppingEntity)
        }
    }

    fun updateShopping(shopping: ShoppingEntity) {
        GlobalScope.launch {
            shoppingRepositoryRoom.updateShopping(shopping)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeNewItemToShop(position: Int) {
        shoppingList?.let { list ->
            viewModelScope.launch {
                shoppingRepositoryRoom.deleteItemById(list[position].id)
            }
        }
    }

    fun getSumOfPrices(): LiveData<Double> {
        return shoppingRepositoryRoom.getSumOfPrices()
    }

}