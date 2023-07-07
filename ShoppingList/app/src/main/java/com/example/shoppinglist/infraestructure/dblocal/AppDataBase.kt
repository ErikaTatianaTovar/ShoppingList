package com.example.shoppinglist.infraestructure.dblocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.daos.ShoppingDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Database(
    entities = [MarketEntity::class, ShoppingEntity::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun marketDao(): MarketDao
    abstract fun shoppingDao(): ShoppingDao

    companion object {

        private const val databaseName = "app_database"

        @Volatile
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}