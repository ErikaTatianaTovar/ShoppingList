package com.example.shoppinglist.domain.models

import java.io.Serializable

data class Shopping(
    val id: Long = 0,
    var nameOfProduct: String,
    var price: Double,
    var quantity: Int,
) : Serializable