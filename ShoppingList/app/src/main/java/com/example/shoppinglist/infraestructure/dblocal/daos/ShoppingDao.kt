package com.example.shoppinglist.infraestructure.dblocal.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping_table WHERE idShopping = :idShopping")
    fun getAllShopping(idShopping: Int): List<ShoppingEntity>
    @Query("SELECT SUM(unitPrice * quantity) FROM shopping_table")
    fun getCalculateTotalPricePerProduct(): LiveData<Double>

    @Query("SELECT SUM(totalPricePerProduct) FROM shopping_table")
    fun getSumOfPrices(): LiveData<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ShoppingEntity>)
}