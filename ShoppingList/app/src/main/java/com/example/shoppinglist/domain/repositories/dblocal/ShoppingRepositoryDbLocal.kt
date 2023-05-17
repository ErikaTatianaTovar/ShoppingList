package com.example.shoppinglist.domain.repositories.dblocal

import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping

interface ShoppingRepositoryDbLocal {
    fun getAllShopping(idCategory: Int): List<Shopping>
    suspend fun insertAll(entities: List<ShoppingEntity>)
}