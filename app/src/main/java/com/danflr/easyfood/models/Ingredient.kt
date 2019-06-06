package com.danflr.easyfood.models

import android.database.Cursor
import com.google.gson.GsonBuilder
import org.json.JSONObject

class Ingredient {
    lateinit var ingredientName: String
    lateinit var categoryName: String
    lateinit var unit: String
    var amount: Int = 0
    lateinit var ingredientId: String
    lateinit var categoryId: String
    lateinit var user_id: String

    constructor(name: String, category: String, unit: String, amount: Int, id: String, categoryId: String, userId: String){
        this.ingredientName = name
        this.categoryName = category
        this.unit = unit
        this.amount = amount
        this.ingredientId = id
        this.categoryId = categoryId
        this.user_id = userId
    }

    constructor(data: JSONObject){
        this.ingredientId = if(!data.isNull("ingredient_Id")) data.getString("ingredient_Id") else ""
        this.categoryName = if(!data.isNull("category")) data.getString("category") else ""
        this.unit = if(!data.isNull("unit")) data.getString("unit") else ""
        this.amount = if(!data.isNull("amount")) data.getInt("amount") else 0
        this.ingredientName = if(!data.isNull("ingredient_Name")) data.getString("ingredient_Name") else ""
        this.categoryId = if(!data.isNull("category_Id")) data.getString("category_Id") else ""
        this.user_id = if(!data.isNull("user_id")) data.getString("user_id") else ""
    }

    constructor(cursor: Cursor){
        this.ingredientId = cursor.getString(4)
        this.user_id = ""
        this.categoryId = ""
        this.ingredientName = cursor.getString(0)
        this.categoryName = cursor.getString(1)
        this.amount = cursor.getInt(3)
        this.unit = cursor.getString(2)
    }

    fun toJson() : String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

}