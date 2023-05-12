package com.example.shoppinglist.infraestructure.dblocal.dtos

import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping


fun List<Shopping>.toShoppingEntity(idCategory: Int): List<ShoppingEntity> {
    return map { shopping ->
        ShoppingEntity(
            id = shopping.id,
            nameOfProduct = shopping.nameOfProduct,
            value = shopping.value,
            quantity = shopping.quantity,
        )
    }
}

fun List<ShoppingEntity>.toDomainModel(): List<Shopping> {
    return map { shoppingEntity ->
        Shopping(
            id = shoppingEntity.id,
            nameOfProduct = shoppingEntity.nameOfProduct,
            value = shoppingEntity.value,
            quantity = shoppingEntity.quantity,
        )
    }
}
