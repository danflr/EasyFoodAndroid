package com.danflr.easyfood.models

import com.google.gson.GsonBuilder
import org.json.JSONObject

class Comment {
    lateinit var Comment_Id: String
    lateinit var Recipe_Id: String
    lateinit var Account_Id: String
    lateinit var User: String
    lateinit var Content: String
    lateinit var Posted_On: String

    constructor(recipeId: String, accountId: String, content: String){
        this.Recipe_Id = recipeId
        this.Account_Id = accountId
        this.Content = content
    }

    constructor(data: JSONObject){
        this.Comment_Id = if(!data.isNull("comment_Id")) data.getString("comment_Id") else ""
        this.Recipe_Id = if(!data.isNull("recipe_Id")) data.getString("recipe_Id") else ""
        this.Account_Id = if(!data.isNull("account_Id")) data.getString("account_Id") else ""
        this.User = if(!data.isNull("user")) data.getString("user") else ""
        this.Content = if(!data.isNull("content")) data.getString("content") else ""
        this.Posted_On = if(!data.isNull("posted_On")) data.getString("posted_On") else ""
    }

    fun toJson() : String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

}