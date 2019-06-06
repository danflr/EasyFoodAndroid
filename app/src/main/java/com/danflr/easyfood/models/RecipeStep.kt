package com.danflr.easyfood.models

import org.json.JSONObject

class RecipeStep{
    var step: Int = 0
    var content: String = ""

    constructor(step: Int, content: String){
        this.step = step;
        this.content = content;
    }

    constructor(data: JSONObject){
        step = if(data.has("step")) data.getInt("step") else 0
        content = if(data.has("content")) data.getString("content") else ""
    }

}