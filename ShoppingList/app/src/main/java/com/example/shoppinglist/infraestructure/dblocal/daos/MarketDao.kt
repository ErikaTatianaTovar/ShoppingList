package com.example.shoppinglist.infraestructure.dblocal.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity

@Dao
interface MarketDao {
    @Query("SELECT * FROM market_table")
    fun getAllMarket(): LiveData<List<MarketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMarket(entity: MarketEntity)

    @Update
    fun updateMarket(market: MarketEntity)

    @Query("DELETE FROM market_table WHERE idMarket = :itemId")
    suspend fun deleteItemById(itemId: Int)
}