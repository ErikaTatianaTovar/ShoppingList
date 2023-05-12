package com.example.shoppinglist.infraestructure.dblocal.daos

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

interface ShoppingDao {

        @Query("SELECT * FROM Movie WHERE idCategory = :idCategory")
        fun getAllShopping(idCategory: Int): List<ShoppingEntity>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertAll(entities: List<ShoppingEntity>)
    }