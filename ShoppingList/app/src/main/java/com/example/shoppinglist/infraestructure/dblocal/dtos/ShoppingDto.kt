package com.example.shoppinglist.infraestructure.dblocal.dtos

import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity
import com.example.shoppinglist.domain.models.Shopping


fun List<Shopping>.toShoppingEntity(): List<ShoppingEntity> {
    return mapIndexed { position, shopping ->
        ShoppingEntity(
            id = position.toLong(),
            nameOfProduct = shopping.nameOfProduct,
            price = shopping.price,
            quantity = shopping.quantity,
        )
    }
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
