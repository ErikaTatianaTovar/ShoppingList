package com.example.shoppinglist.infraestructure.dblocal.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.repositories.dblocal.MarketRepositoryDbLocal
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import javax.inject.Inject

class MarketListRepositoryRoom @Inject constructor(
    private val marketDao: MarketDao
) : MarketRepositoryDbLocal {

    override fun getAllMarket(): LiveData<List<MarketEntity>> {
        return marketDao.getAllMarket()
    }

    override suspend fun insertMarket(entity: MarketEntity) {
        marketDao.insertMarket(entity)
    }

    override suspend fun updateMarket(market: MarketEntity) {
        marketDao.updateMarket(market)
    }

    suspend fun deleteItemById(itemId: Int) {
        marketDao.deleteItemById(itemId)
    }
}