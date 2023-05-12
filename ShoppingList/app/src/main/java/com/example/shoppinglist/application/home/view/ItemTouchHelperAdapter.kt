package com.example.shoppinglist.application.home.view

interface ItemTouchHelperAdapter {
    fun onItemDismiss(position: Int)

    fun onItemMove(fromPosition: Int, toPosition: Int)
}