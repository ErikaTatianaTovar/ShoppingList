package com.example.shoppinglist.infraestructure.dblocal.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.repositories.dblocal.ShoppingRepositoryDbLocal
import com.example.shoppinglist.infraestructure.dblocal.AppDataBase
import com.example.shoppinglist.infraestructure.dblocal.daos.ShoppingDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import javax.inject.Inject

class ShoppingRepositoryRoom @Inject constructor(
    private val shoppingDao: ShoppingDao
) : ShoppingRepositoryDbLocal {

    override fun getAllShopping(): LiveData<List<ShoppingEntity>> {
        return shoppingDao.getAllShopping()
    }

    override suspend fun insertShopping(entity: ShoppingEntity) {
        shoppingDao.insertShopping(entity)
    }

    override suspend fun updateShopping(shopping: ShoppingEntity) {
        shoppingDao.updateShopping(shopping)
    }

    fun getSumOfPrices(): LiveData<Double> {
        return shoppingDao.getSumOfPrices()
    }

    suspend fun deleteItemById(itemId: Int) {
        shoppingDao.deleteItemById(itemId)
    }
}