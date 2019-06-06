package com.danflr.easyfood.helpers

import android.content.Context
import android.preference.PreferenceManager

class PreferencesHelper(ctx: Context){
    private val SHOW_DOABLE_RECIPES_ONLY = "data.source.prefs.SHOW_DOABLE_RECIPES_ONLY";
    private val USERNAME = "data.source.prefs.USERNAME";
    private val PASSWORD = "data.source.prefs.PASSWORD";
    private val USER_ID = "data.source.prefs.USER_ID";
    private val EMAIL_ADDRESS = "data.source.prefs.EMAIL_ADDRESS";
    private val LOGGED_IN = "data.source.prefs.LOGGED_IN";
    private val SERVER_ADDRESS = "data.source.prefs.SERVER_ADDRESS"

    private val preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

    var isLoggedIn = preferences.getBoolean(LOGGED_IN, false)
        set(value) = preferences.edit().putBoolean(LOGGED_IN, value).apply()

    var username = preferences.getString(USERNAME, "")
        set(value) = preferences.edit().putString(USERNAME, value).apply()

    var password = preferences.getString(PASSWORD, "")
        set(value) = preferences.edit().putString(PASSWORD, value).apply()

    var userId = preferences.getString(USER_ID, "")
        set(value) = preferences.edit().putString(USER_ID, value).apply()

    var email = preferences.getString(EMAIL_ADDRESS, "")
        set(value) = preferences.edit().putString(EMAIL_ADDRESS, value).apply()

    var showOnlyDoableRecipes = preferences.getBoolean(SHOW_DOABLE_RECIPES_ONLY, true)
        set(value) = preferences.edit().putBoolean(SHOW_DOABLE_RECIPES_ONLY, value).apply()

    var currentAddress = preferences.getString(SERVER_ADDRESS, "https://easyfood-api.conveyor.cloud/api/v1")
        set(value) = preferences.edit().putString(SERVER_ADDRESS, value).apply()

}