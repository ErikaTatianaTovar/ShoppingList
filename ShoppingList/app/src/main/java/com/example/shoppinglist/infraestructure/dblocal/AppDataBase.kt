package com.example.shoppinglist.infraestructure.dblocal

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shoppinglist.infraestructure.dblocal.daos.MarketDao
import com.example.shoppinglist.infraestructure.dblocal.daos.ShoppingDao
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.entitys.ShoppingEntity

@Database(
    entities = [MarketEntity::class, ShoppingEntity::class],
    version = 3
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun marketDao(): MarketDao
    abstract fun shoppingDao(): ShoppingDao

    private val _shoppingList = MutableLiveData<List<ShoppingEntity>>()
    val shoppingList: LiveData<List<ShoppingEntity>> get() = _shoppingList

    companion object {

        const val databaseName = "app_database"

        @Volatile
        private var instance: AppDataBase? = null
        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }

            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        instance?.runInTransaction {
                            val shoppingDao = instance?.shoppingDao()
                            val emptyItem = ShoppingEntity(0, "", 0.0, 0)
                            shoppingDao?.insertShopping(emptyItem)

                            val shoppingListLiveData = shoppingDao?.getAllShopping()
                            shoppingListLiveData?.observeForever { shoppingList ->
                                instance?._shoppingList?.value = shoppingList
                            }
                        }
                    }
                })
                .build()
        }
    }
}