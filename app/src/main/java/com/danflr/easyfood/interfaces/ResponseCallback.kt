package com.danflr.easyfood.interfaces

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

interface ResponseCallback {

    fun onSuccessResponse(response: JSONObject){
        Log.i("Response received", response.toString(4))
    }

    fun onSuccessResponse(response: JSONArray){
        Log.i("Response received", response.toString(4))
    }

    fun onErrorResponse(){
        Log.i("Error response received", Date().toString())
    }

    fun onErrorResponse(errorMessage: String){
        Log.i("Error response received", errorMessage)
    }

}