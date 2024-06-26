package com.example.shoppinglist.domain.models

import java.io.Serializable

data class Shopping(
    val id: Int,
    var nameOfProduct: String,
    var price: Double,
    var quantity: Int,
) : Serializable