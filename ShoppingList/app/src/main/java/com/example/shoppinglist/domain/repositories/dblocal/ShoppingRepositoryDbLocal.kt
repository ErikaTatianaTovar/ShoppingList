package com.example.shoppinglist.domain.repositories.dblocal

import androidx.lifecycle.LiveData
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

interface ShoppingRepositoryDbLocal {
    fun getAllShopping(): LiveData<List<ShoppingEntity>>
    suspend fun insertShopping(entities: ShoppingEntity)
    suspend fun updateShopping (shopping: ShoppingEntity)
}