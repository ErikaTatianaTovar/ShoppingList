package com.example.shoppinglist.domain.models

import java.io.Serializable

data class Shopping(
    val id: Int,
    val nameOfProduct: String,
    val value: Int,
    val quantity: Int,
) : Serializable