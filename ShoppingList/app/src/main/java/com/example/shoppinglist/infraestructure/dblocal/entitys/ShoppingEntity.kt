package com.example.shoppinglist.infraestructure.dblocal.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Shopping_table")
data class ShoppingEntity(
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "idShopping")
    val id:Long = 0,
    val nameOfProduct: String,
    val price: Double,
    val quantity: Int,
)