package com.example.shoppinglist.application.marketlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.models.Market
import com.example.shoppinglist.infraestructure.dblocal.entitys.MarketEntity
import com.example.shoppinglist.infraestructure.dblocal.repositories.MarketListRepositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketListViewModel @Inject constructor(private var marketRepositoryRoom: MarketListRepositoryRoom) :
    ViewModel() {

    var marketList: List<Market>? = null

    fun getAllMarket() = marketRepositoryRoom.getAllMarket()

    fun addNewItemMarketList(marketEntity: MarketEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            marketRepositoryRoom.insertMarket(marketEntity)
        }
    }

    fun updateMarket(market: MarketEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            marketRepositoryRoom.updateMarket(market)
        }
    }

    fun removeNewItemToMarketList(position: Int) {
        marketList?.let { list ->
            viewModelScope.launch(Dispatchers.IO) {
                marketRepositoryRoom.deleteItemById(list[position].id)
            }
        }
    }
}