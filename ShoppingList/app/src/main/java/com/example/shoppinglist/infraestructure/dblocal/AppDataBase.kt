package com.example.shoppinglist.infraestructure.dblocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.daos.ShoppingDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Database(entities = [MarketEntity::class, ShoppingEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun marketDao(): MarketDao
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
