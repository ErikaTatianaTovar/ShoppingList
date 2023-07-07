package com.example.shoppinglist.infraestructure.dblocal.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity

@Dao
interface MarketDao {

    @Query("SELECT * FROM market_table WHERE idMarket = :idMarket")
    fun getAllMarket(idMarket: Int): List<MarketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<MarketEntity>)
}