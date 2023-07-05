package com.example.shoppinglist.infraestructure.dblocal.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping_table")
    fun getAllShopping(): LiveData<List<ShoppingEntity>>

    @Query("SELECT SUM(price) FROM shopping_table")
    fun getSumOfPrices(): LiveData<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShopping(entity: ShoppingEntity)

    @Update
    fun updateShopping(shopping: ShoppingEntity)

    @Query("DELETE FROM shopping_table WHERE idShopping = :itemId")
    suspend fun deleteItemById(itemId: Int)
}