package com.example.shoppinglist.domain.models

import java.io.Serializable

data class Market(
    val id: Int,
    var nameOfProduct: String,
    var quantity: Int,
) : Serializable