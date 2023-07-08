package com.example.shoppinglist.infraestructure.dblocal.dtos

import com.example.shoppinglist.domain.models.Market
import com.example.shoppinglist.domain.models.Shopping
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

fun Market.toMarketEntity(): MarketEntity {
    return MarketEntity(
        id = this.id,
        nameOfProduct = this.nameOfProduct,
        quantity = this.quantity,
    )
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