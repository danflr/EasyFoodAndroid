package com.danflr.easyfood.interfaces

import android.util.Log

interface ItemTap {

    fun onTap(itemId: String){
        Log.i("Item tapped!", itemId)
    }

}