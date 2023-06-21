package com.example.shoppinglist.infraestructure.dblocal.dtos

import com.example.shoppinglist.domain.models.Market
import com.example.shoppinglist.domain.models.Shopping
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

fun List<Market>.toMarketEntity(): List<MarketEntity> {
    return mapIndexed { position, market ->
        MarketEntity(
            id = position,
            nameOfProduct = market.nameOfProduct,
            quantity = market.quantity,
        )
    }
}

fun List<MarketEntity>.toDomainModel(): List<Market> {
    return map { marketEntity ->
        Market(
            id = marketEntity.id,
            nameOfProduct = marketEntity.nameOfProduct,
            quantity = marketEntity.quantity,
        )
    }
}