package com.danflr.easyfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.adapters.AddIngredientAdapter
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Ingredient
import kotlinx.android.synthetic.main.activity_ingredients.*
import org.json.JSONArray

class IngredientsActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue
    lateinit var ingredients: ArrayList<Ingredient>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)

        queue = Volley.newRequestQueue(this)
        ingredients = ArrayList<Ingredient>()
        val act = this

        RequestHelper(this).getAllIngredients(queue, object: ResponseCallback {
            override fun onSuccessResponse(response: JSONArray) {
                for(i in 0 .. response.length() -1){
                    ingredients.add(Ingredient(response.getJSONObject(i)))
                    lvIngredients.adapter = AddIngredientAdapter(ingredients, act)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){

            android.R.id.home -> {
                val bottomNavDrawerFragment = BottomDrawerDialogFragment()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }

            R.id.app_bar_settings -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
