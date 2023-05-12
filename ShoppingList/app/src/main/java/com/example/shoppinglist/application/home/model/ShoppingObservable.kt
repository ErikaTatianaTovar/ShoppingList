package com.example.shoppinglist.application.home.model

import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.models.Shopping

class ShoppingObservable {
    //repositorio
    fun callShopping(){
    }
    //view model
    fun getShopping(): MutableLiveData<List<Shopping>> {
        return getShopping()
    }
}
