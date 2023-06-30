package com.example.shoppinglist.infraestructure.dblocal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.daos.ShoppingDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Database(entities = [MarketEntity::class, ShoppingEntity::class], version = 3)
abstract class AppDataBase : RoomDatabase() {
    abstract fun marketDao(): MarketDao
    abstract fun shoppingDao(): ShoppingDao

    private val _shoppingList = MutableLiveData<List<ShoppingEntity>>()
    val shoppingList: LiveData<List<ShoppingEntity>> get() = _shoppingList

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance.runInTransaction {
                    val shoppingDao = instance.shoppingDao()
                    val emptyItem = ShoppingEntity(0, "", 0.0, 0)
                    shoppingDao.insertShopping(emptyItem)

                    val shoppingListLiveData = shoppingDao.getAllShopping()
                    shoppingListLiveData.observeForever { shoppingList ->
                        instance._shoppingList.value = shoppingList
                    }
                }
                instance
            }
        }
    }
}