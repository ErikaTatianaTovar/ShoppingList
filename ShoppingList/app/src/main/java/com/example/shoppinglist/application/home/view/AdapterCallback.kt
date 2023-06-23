package com.example.shoppinglist.application.home.view

interface AdapterCallback {
    fun onValueUpdated(position: Int, value: String, idShopping: Int)
}