package com.danflr.easyfood.helpers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.danflr.easyfood.MainActivity
import com.danflr.easyfood.R
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Account
import com.danflr.easyfood.models.Login
import com.danflr.easyfood.models.UserIngredient
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.util.prefs.PreferenceChangeEvent

class RequestHelper(ctx: Context) {

    private val ctx: Context = ctx
    private val API_BASE_ENDPOINT = PreferencesHelper(ctx).currentAddress
    private val RECIPES_CONTROLLER = API_BASE_ENDPOINT + "/recipes"
    private val RECIPE_CONTROLLER = API_BASE_ENDPOINT + "/recipes/{id}"
    private val DOABLE_RECIPES_CONTROLLER = API_BASE_ENDPOINT + "/userrecipes/{userid}"
    private val PROFILE_CONTROLLER = API_BASE_ENDPOINT + "/accounts/{username}"
    private val REGISTER_CONTROLLER = API_BASE_ENDPOINT + "/accounts"
    private val LOGIN_CONTROLLER = API_BASE_ENDPOINT + "/login"
    private val INGREDIENTS_CONTROLLER = API_BASE_ENDPOINT + "/ingredients"
    private val USER_INGREDIENTS_CONTROLLER = API_BASE_ENDPOINT + "/ingredients/{username}"

    fun logIn(account: Login, queue: RequestQueue, callback: ResponseCallback){
        val data = GsonBuilder().setPrettyPrinting().create().toJson(account);
        val prefs = PreferencesHelper(ctx)
        Log.i("=============Logging in", data)
        try {
            val request = object : StringRequest(
                Method.POST,
                LOGIN_CONTROLLER,
                Response.Listener<String> { response ->
                    callback.onSuccessResponse(JSONObject(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse(errorMsg)
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }

                override fun getBody(): ByteArray {
                    try {
                        return if(data == null) ByteArray(0) else data.toByteArray(Charsets.UTF_8)
                    } catch(ex: UnsupportedEncodingException){
                        return ByteArray(0)
                    }
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }


    fun register(account: Account, queue: RequestQueue, callback: ResponseCallback){
        val data = account.toJson()
        val prefs = PreferencesHelper(ctx)
        Log.i("=======Creating account", data)
        try {
            val request = object : StringRequest(
                Method.POST,
                REGISTER_CONTROLLER,
                Response.Listener<String> { response ->
                    callback.onSuccessResponse(JSONObject(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse(errorMsg)
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }

                override fun getBody(): ByteArray {
                    try {
                        return if(data == null) ByteArray(0) else data.toByteArray(Charsets.UTF_8)
                    } catch(ex: UnsupportedEncodingException){
                        return ByteArray(0)
                    }
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun getRecipes(queue: RequestQueue, callback: ResponseCallback){
        val prefs = PreferencesHelper(ctx)
        Log.i("===Getting recipes from", if(prefs.showOnlyDoableRecipes) DOABLE_RECIPES_CONTROLLER.replace("{userid}", prefs.userId) else RECIPES_CONTROLLER)
        try {
            val request = object : StringRequest(
                Method.GET,
                if(prefs.showOnlyDoableRecipes) DOABLE_RECIPES_CONTROLLER.replace("{userid}", prefs.userId) else RECIPES_CONTROLLER,
                Response.Listener<String> { response ->
                    Log.i("Received", response)
                    callback.onSuccessResponse(JSONArray(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun getFullRecipe(recipeID: String, queue: RequestQueue, callback: ResponseCallback){
        val prefs = PreferencesHelper(ctx)
        Log.i("=========Getting recipe", recipeID)
        try {
            val request = object : StringRequest(
                Method.GET,
                RECIPE_CONTROLLER.replace("{id}", recipeID),
                Response.Listener<String> { response ->
                    Log.i("===============Received", response)
                    callback.onSuccessResponse(JSONObject(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun getProfileFromUsername(username: String, queue: RequestQueue, callback: ResponseCallback){
        val prefs = PreferencesHelper(ctx)
        Log.i("========Getting profile", username)
        try {
            val request = object : StringRequest(
                Method.GET,
                PROFILE_CONTROLLER.replace("{username}", username),
                Response.Listener<String> { response ->
                    Log.i("Received", response)
                    callback.onSuccessResponse(JSONObject(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun getAllIngredients(queue: RequestQueue, callback: ResponseCallback){
        val prefs = PreferencesHelper(ctx)
        Log.i("Getting ingredient list", "================================")
        try {
            val request = object : StringRequest(
                Method.GET,
                INGREDIENTS_CONTROLLER,
                Response.Listener<String> { response ->
                    Log.i("Received", response)
                    callback.onSuccessResponse(JSONArray(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun getUserIngredients(queue: RequestQueue, callback: ResponseCallback){
        val prefs = PreferencesHelper(ctx)
        Log.i("Get user ingredients", prefs.username)
        try {
            val request = object : StringRequest(
                Method.GET,
                USER_INGREDIENTS_CONTROLLER.replace("{username}", prefs.username),
                Response.Listener<String> { response ->
                    Log.i("Received", response)
                    callback.onSuccessResponse(JSONArray(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }
            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun addUserIngredient(ingredient: UserIngredient, queue: RequestQueue, callback: ResponseCallback){
        val prefs = PreferencesHelper(ctx)
        val data = ingredient.toJson()
        Log.i("Adding ingredient", data)
        try {
            val request = object : StringRequest(
                Method.POST,
                INGREDIENTS_CONTROLLER,
                Response.Listener<String> { response ->
                    Log.i("Received", response)
                    callback.onSuccessResponse(JSONObject(response.toString()))
                }, Response.ErrorListener {
                    val errorMsg = getErrorMessage(it);
                    showResponseError(errorMsg)
                    callback.onErrorResponse()
                }
            ) {
                override fun getBodyContentType(): String {
                    return "application/json"
                }

                override fun getHeaders(): MutableMap<String, String> {
                    return HashMap<String, String>();
                }

                override fun getBody(): ByteArray {
                    try {
                        return if(data == null) ByteArray(0) else data.toByteArray(Charsets.UTF_8)
                    } catch(ex: UnsupportedEncodingException){
                        return ByteArray(0)
                    }
                }

            }
            queue.add(request);
        } catch(ex: Exception){
            showResponseError(ex.message!!);
        }
    }

    fun getErrorMessage(it: VolleyError) : String {
        var msg = ""
        if(it.networkResponse != null) {
            when(it.networkResponse.statusCode){
                301 -> msg = ctx.getString(R.string.server_path_moved) // 300: Moved permanently
                400 -> msg = ctx.getString(R.string.error_bad_request) // 400: Bad request
                401 -> { // 401: Unauthorized
                    msg = ctx.getString(R.string.response_unauthorized)
                }
                403 -> msg = ctx.getString(R.string.error_forbidden) // 403: Forbidden
                404 -> msg = ctx.getString(R.string.error_not_found) // 404: Not found
                408 -> msg = ctx.getString(R.string.error_timeout) // 408: Timeout
                500 -> { // 500: Internal server error
                    if(it.networkResponse != null){
                        var jsonResult: JSONObject = JSONObject(String(it.networkResponse.data, Charsets.UTF_8))
                        if(!jsonResult.isNull("ExceptionMessage")){
                            msg = jsonResult.getString("ExceptionMessage")
                        } else {
                            msg = ctx.getString(R.string.response_internal_server_error)
                        }
                    } else {
                        msg = ctx.getString(R.string.response_internal_server_error)
                    }
                }
                502 -> msg = ctx.getString(R.string.error_bad_gateway) // 502: Bad gateway
                else -> {
                    var json = String(it.networkResponse.data)
                    if (json.isNotEmpty()) {
                        val oError = JSONObject(json)
                        if (oError.has("ExceptionMessage")) {
                            msg = oError.getString("ExceptionMessage")
                        } else {
                            msg = ctx.getString(R.string.unexpected_error) + ": " + it.networkResponse.statusCode
                        }
                    } else {
                        msg = ctx.getString(R.string.unexpected_error) + ": " + it.networkResponse.statusCode
                    }
                }
            }
        } else {
            msg = ctx.getString(R.string.error_server_unresponsive)
        }
        return msg;
    }

    fun showResponseError(msg: String){
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
    }

}