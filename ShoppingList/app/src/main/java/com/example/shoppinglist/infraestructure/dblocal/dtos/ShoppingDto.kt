package com.example.shoppinglist.infraestructure.dblocal.dtos

import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping


fun Shopping.toShoppingEntity(): ShoppingEntity {
    return ShoppingEntity(
        id = this.id,
        nameOfProduct = this.nameOfProduct,
        price = this.price,
        quantity = this.quantity,
    )
}

fun List<ShoppingEntity>.toDomainModel(): List<Shopping> {
    return map { shoppingEntity ->
        Shopping(
            id = shoppingEntity.id,
            nameOfProduct = shoppingEntity.nameOfProduct,
            price = shoppingEntity.price,
            quantity = shoppingEntity.quantity,
        )
    }
}
