package com.example.shoppinglist.infraestructure.dblocal.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "market_table")
data class MarketEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMarket")
    val id: Int,
    val nameOfProduct: String,
    val quantity: Int,
)