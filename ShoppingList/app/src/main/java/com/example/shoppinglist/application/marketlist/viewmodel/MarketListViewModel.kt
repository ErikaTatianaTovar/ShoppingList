package com.example.shoppinglist.application.marketlist.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.application.marketlist.view.RecyclerMarketListAdapter
import com.example.shoppinglist.domain.models.Market
import com.example.shoppinglist.infraestructure.dblocal.AppDataBase
import com.example.shoppinglist.infraestructure.dblocal.dtos.toMarketEntity
import com.example.shoppinglist.infraestructure.dblocal.repositories.MarketListRepositoryRoom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MarketListViewModel() : ViewModel() {

    val recyclerMarketListAdapter: RecyclerMarketListAdapter = RecyclerMarketListAdapter(this)
    val marketList: ArrayList<Market> = arrayListOf(Market(0, "", 0))
    private lateinit var marketRepositoryRoom: MarketListRepositoryRoom
    lateinit var lifecycleOwner: LifecycleOwner

    fun createDB(context: Context){
        val marketDao = AppDataBase.getInstance(context).marketDao()
        marketRepositoryRoom = MarketListRepositoryRoom(marketDao)
    }

    fun addNewItemMarketList() {
        val market = Market(0, "", 0)
        marketList.add(market)
        recyclerMarketListAdapter.notifyDataSetChanged()
        GlobalScope.launch{
            marketRepositoryRoom.insertAll(marketList.toMarketEntity())
        }
    }

    fun removeNewItemToMarketList(position:Int) {
        marketList.removeAt(position)
        recyclerMarketListAdapter.notifyDataSetChanged()
    }

}