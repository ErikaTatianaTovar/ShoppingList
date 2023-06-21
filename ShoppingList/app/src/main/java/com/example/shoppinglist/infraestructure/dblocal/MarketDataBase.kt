package com.example.shoppinglist.infraestructure.dblocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity

@Database(entities = [MarketEntity::class], version = 2)
abstract class MarketDataBase : RoomDatabase() {

    abstract fun marketDao(): MarketDao

    companion object {
        @Volatile
        private var INSTANCE: MarketDataBase? = null

        fun getInstance(context: Context): MarketDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarketDataBase::class.java,
                    "market_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}