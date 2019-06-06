package com.danflr.easyfood.models

import com.google.gson.GsonBuilder
import org.json.JSONObject

class Account {
    lateinit var username: String
    lateinit var pass: String
    lateinit var email: String

    constructor(account: JSONObject){
        this.username = if(!account.isNull("Username")) account.getString("Username") else "";
        this.pass = if(!account.isNull("Pass")) account.getString("Pass") else "";
        this.email = if(!account.isNull("Email")) account.getString("Email") else "";
    }

    constructor(username: String, pass: String, email: String){
        this.username = username;
        this.pass = pass;
        this.email = email;
    }

    fun toJson() : String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

}