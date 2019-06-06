package com.danflr.easyfood.models

import org.json.JSONObject

class Profile {
    lateinit var username: String
    lateinit var recipes: ArrayList<Recipe>

    constructor(data: JSONObject){
        username = if(!data.isNull("username")) data.getString("username") else ""
        recipes = ArrayList<Recipe>()
        var json = data
        if(!json.isNull("recipes")){
            var recipeArray = json.getJSONArray("recipes")
            for(i in 0 .. recipeArray.length() - 1){
                recipes.add(Recipe(recipeArray.getJSONObject(i)))
            }
        }
    }

}