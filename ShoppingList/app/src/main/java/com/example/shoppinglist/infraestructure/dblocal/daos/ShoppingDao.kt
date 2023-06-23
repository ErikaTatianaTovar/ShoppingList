package com.example.shoppinglist.infraestructure.dblocal.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping_table")
    fun getAllShopping(): List<ShoppingEntity>

    @Query("UPDATE Shopping_table SET totalPricePerProduct = unitPrice * quantity WHERE idShopping = :idShopping")
    fun getCalculateTotalPricePerProduct(idShopping: Int)


    @Query("SELECT SUM(totalPricePerProduct) FROM shopping_table")
    fun getSumOfPrices(): LiveData<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ShoppingEntity>)
}