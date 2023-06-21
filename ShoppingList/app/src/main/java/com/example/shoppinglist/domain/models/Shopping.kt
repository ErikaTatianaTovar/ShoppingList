package com.example.shoppinglist.domain.models

import java.io.Serializable

data class Shopping(
    val id: Int,
    var nameOfProduct: String,
    var unitPrice: Double,
    var quantity: Int,
    var totalPricePerProduct: Double,
) : Serializable