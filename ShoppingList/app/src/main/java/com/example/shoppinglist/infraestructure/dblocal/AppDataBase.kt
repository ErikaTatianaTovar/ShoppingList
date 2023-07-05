package com.example.shoppinglist.infraestructure.dblocal

import android.content.Context
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
                    //.addCallback(object : Callback() {
                      //  override fun onCreate(db: SupportSQLiteDatabase) {
                        //    super.onCreate(db)
                         //   instance?.runInTransaction {
                             /*   val shoppingDao = instance?.shoppingDao()
                                val firstItem = ShoppingEntity(0, "", 0.0, 0)
                                shoppingDao?.insertShopping(firstItem)
                            }
                      //  }
                    })  */

                    .build()
        }
    }
}