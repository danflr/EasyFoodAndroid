package com.danflr.easyfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.adapters.IngredientAdapter
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Ingredient
import kotlinx.android.synthetic.main.activity_user_ingredients.*
import org.json.JSONArray

class UserIngredientsActivity : AppCompatActivity() {
    lateinit var queue: RequestQueue
    lateinit var ingredients: ArrayList<Ingredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ingredients)

        queue = Volley.newRequestQueue(this)
        ingredients = ArrayList<Ingredient>()
        val act = this

        RequestHelper(this).getUserIngredients(queue, object: ResponseCallback {
            override fun onSuccessResponse(response: JSONArray) {
                for(i in 0 .. response.length() - 1){
                    ingredients.add(Ingredient(response.getJSONObject(i)))
                }
                lvIngredients.adapter = IngredientAdapter(ingredients, act, true)
            }
        })

    }
}
