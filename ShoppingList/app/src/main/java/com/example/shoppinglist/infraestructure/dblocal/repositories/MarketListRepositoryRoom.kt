package com.example.shoppinglist.infraestructure.dblocal.repositories

import com.example.shoppinglist.domain.models.Market
import com.example.shoppinglist.infraestructure.dblocal.dtos.toDomainModel
import com.example.shoppinglist.domain.repositories.dblocal.MarketRepositoryDbLocal
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import javax.inject.Inject

class MarketListRepositoryRoom @Inject constructor(
    private val marketDao: MarketDao
) : MarketRepositoryDbLocal {

    override fun getAllMarket(idCategory: Int): List<Market> {
        return marketDao.getAllMarket(idCategory).toDomainModel()
    }

    override suspend fun insertAll(entities: List<MarketEntity>) {
        return marketDao.insertAll(entities)
    }
}