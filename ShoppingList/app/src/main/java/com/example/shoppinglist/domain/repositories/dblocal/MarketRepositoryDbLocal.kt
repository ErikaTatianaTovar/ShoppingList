package com.example.shoppinglist.domain.repositories.dblocal

import com.example.shoppinglist.domain.models.Market
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity

interface MarketRepositoryDbLocal {
    fun getAllMarket(idCategory: Int): List<Market>
    suspend fun insertAll(entities: List<MarketEntity>)
}