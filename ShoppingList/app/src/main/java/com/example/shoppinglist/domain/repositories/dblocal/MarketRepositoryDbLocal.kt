package com.example.shoppinglist.domain.repositories.dblocal

import androidx.lifecycle.LiveData
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity

interface MarketRepositoryDbLocal {
    fun getAllMarket(): LiveData<List<MarketEntity>>
    suspend fun insertMarket(entities: MarketEntity)
    suspend fun updateMarket(market: MarketEntity)
}