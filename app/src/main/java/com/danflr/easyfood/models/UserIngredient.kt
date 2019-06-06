package com.danflr.easyfood.models

import com.google.gson.GsonBuilder

class UserIngredient(userID: String, ingredientID: String, amount: Int) {
    var user_id = userID
    var ingredient_id = ingredientID
    var amount = amount

    fun toJson() : String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

}