package com.danflr.easyfood.models

import com.google.gson.GsonBuilder

class Login(username: String, pass: String) {
    var username = username;
    var pass = pass;

    fun toJson() : String {
        return GsonBuilder().setPrettyPrinting().create().toJson(this)
    }

}