package com.example.shoppinglist.infraestructure.dblocal.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.repositories.dblocal.ShoppingRepositoryDbLocal
import com.example.shoppinglist.infraestructure.dblocal.daos.ShoppingDao
import com.example.shoppinglist.infraestructure.dblocal.dtos.toDomainModel
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping

class ShoppingRepositoryRoom constructor(
    private val shoppingDao: ShoppingDao
) : ShoppingRepositoryDbLocal {

    override fun getAllShopping(idCategory: Int): List<Shopping> {
        return shoppingDao.getAllShopping(idCategory).toDomainModel()
    }

    override suspend fun insertAll(entities: List<ShoppingEntity>) {
        return shoppingDao.insertAll(entities)
    }

    fun getCalculateTotalPricePerProduct(): LiveData<Double> {
        return shoppingDao.getCalculateTotalPricePerProduct()
    }

    fun getSumOfPrices(): LiveData<Double> {
        return shoppingDao.getSumOfPrices()
    }
}