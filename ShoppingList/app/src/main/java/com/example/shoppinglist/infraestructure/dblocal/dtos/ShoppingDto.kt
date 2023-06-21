package com.example.shoppinglist.infraestructure.dblocal.dtos

import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping


fun List<Shopping>.toShoppingEntity(): List<ShoppingEntity> {
    return mapIndexed { position, shopping ->
        ShoppingEntity(
            id = position,
            nameOfProduct = shopping.nameOfProduct,
            unitPrice = shopping.unitPrice,
            quantity = shopping.quantity,
            totalPricePerProduct = shopping.totalPricePerProduct,
        )
    }
}

fun List<ShoppingEntity>.toDomainModel(): List<Shopping> {
    return map { shoppingEntity ->
        Shopping(
            id = shoppingEntity.id,
            nameOfProduct = shoppingEntity.nameOfProduct,
            unitPrice = shoppingEntity.unitPrice,
            quantity = shoppingEntity.quantity,
            totalPricePerProduct = shoppingEntity.totalPricePerProduct,
        )
    }
}
