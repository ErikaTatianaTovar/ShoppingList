package com.example.shoppinglist.di

import com.example.shoppinglist.domain.repositories.dblocal.MarketRepositoryDbLocal
import com.example.shoppinglist.domain.repositories.dblocal.ShoppingRepositoryDbLocal
import com.example.shoppinglist.infraestructure.dblocal.repositories.MarketListRepositoryRoom
import com.example.shoppinglist.infraestructure.dblocal.repositories.ShoppingRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class ShoppingListModule {
    @Binds
    abstract fun provideShoppingRepositoryDbLocal(shoppingRepositoryRoom: ShoppingRepositoryRoom): ShoppingRepositoryDbLocal

    @Binds
    abstract fun provideMarketRepositoryDbLocal(marketListRepositoryRoom: MarketListRepositoryRoom): MarketRepositoryDbLocal

}
