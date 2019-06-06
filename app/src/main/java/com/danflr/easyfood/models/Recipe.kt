package com.danflr.easyfood.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.gson.GsonBuilder
import org.json.JSONObject


class Recipe {
    lateinit var recipe_Name: String
    lateinit var recipe_Id: String
    var likes: Int = 0
    lateinit var user: String
    lateinit var postDate: String
    var comments: Int = 0
    lateinit var category: String
    lateinit var commentsList: ArrayList<Comment>
    lateinit var ingredients: ArrayList<Ingredient>
    lateinit var posted_By: String
    lateinit var category_Id: String
    lateinit var steps: ArrayList<RecipeStep>
    lateinit var img: String

    constructor(username: String, poster: String){
        this.recipe_Name = ""
        this.recipe_Id = ""
        this.likes = 0
        this.user = username
        this.postDate = ""
        this.comments = 0
        this.category = ""
        this.commentsList = ArrayList<Comment>()
        this.ingredients = ArrayList<Ingredient>()
        this.posted_By = poster
        this.category_Id = ""
    }

    constructor(data: JSONObject){
        this.recipe_Id = if(!data.isNull("recipe_Id")) data.getString("recipe_Id") else ""
        this.recipe_Name = if(!data.isNull("recipe_Name")) data.getString("recipe_Name") else ""
        this.likes = if(!data.isNull("likes")) data.getInt("likes") else 0
        this.user = if(!data.isNull("user")) data.getString("user") else ""
        this.postDate = if(!data.isNull("postDate")) data.getString("postDate") else ""
        this.comments = if(!data.isNull("comments")) data.getInt("comments") else 0
        this.category = if(!data.isNull("category")) data.getString("category") else ""
        this.commentsList = ArrayList<Comment>()
        this.ingredients = ArrayList<Ingredient>()
        this.steps = ArrayList<RecipeStep>()
        this.img = if(!data.isNull("img")) data.getString("img") else ""
        this.posted_By = if(!data.isNull("posted_By")) data.getString("posted_By") else ""
        this.category_Id = if(!data.isNull("category_Id")) data.getString("category_Id") else ""
        if(!data.isNull("commentsList")){
            for(i in 0 .. data.getJSONArray("commentsList").length() - 1){
                this.commentsList.add(Comment(data.getJSONArray("commentsList").getJSONObject(i)))
            }
        }
        if(!data.isNull("ingredients")){
            for(i in 0 .. data.getJSONArray("ingredients").length() - 1){
                this.ingredients.add(Ingredient(data.getJSONArray("ingredients").getJSONObject(i)))
            }
        }
        if(!data.isNull("steps")){
            for(i in 0 .. data.getJSONArray("steps").length() - 1){
                this.steps.add(RecipeStep(data.getJSONArray("steps").getJSONObject(i)))
            }
        }
    }

    fun toJson() : String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

    fun getImageBitmap() : Bitmap? {
        if(this.img.length > 0) return BitmapFactory.decodeByteArray(Base64.decode(this.img, Base64.DEFAULT), 0, Base64.decode(img, Base64.DEFAULT).size)
        else return null
    }
}