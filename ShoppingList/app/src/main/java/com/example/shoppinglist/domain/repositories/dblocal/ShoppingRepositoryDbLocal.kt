package com.example.shoppinglist.domain.repositories.dblocal

import androidx.lifecycle.LiveData
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping

interface ShoppingRepositoryDbLocal {
    fun getAllShopping(): LiveData<List<ShoppingEntity>>
    suspend fun insertAll(entities: List<ShoppingEntity>)
}