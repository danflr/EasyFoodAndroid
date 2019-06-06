package com.danflr.easyfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.adapters.RecipesAdapter
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Recipe
import kotlinx.android.synthetic.main.activity_recipes.*
import org.json.JSONArray

class RecipesActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue
    lateinit var recipes: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(this)
        recipes = ArrayList<Recipe>()
        val act = this

        refresher.isRefreshing = true

        refresher.setOnRefreshListener {
            RequestHelper(this).getRecipes(queue, object: ResponseCallback {
                override fun onSuccessResponse(response: JSONArray) {
                    for(i in 0 .. response.length() - 1){
                        recipes.add(Recipe(response.getJSONObject(i)))
                    }

                    lvRecipes.adapter = RecipesAdapter(recipes, act, queue)
                    refresher.isRefreshing = false
                }

                override fun onErrorResponse() {
                    refresher.isRefreshing = false
                }
            })
        }

        RequestHelper(this).getRecipes(queue, object: ResponseCallback {
            override fun onSuccessResponse(response: JSONArray) {
                for(i in 0 .. response.length() - 1){
                    recipes.add(Recipe(response.getJSONObject(i)))
                }

                lvRecipes.adapter = RecipesAdapter(recipes, act, queue)
                refresher.isRefreshing = false
            }

            override fun onErrorResponse() {
                refresher.isRefreshing = false
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

            R.id.app_bar_search -> {
                Toast.makeText(this, "Búsqueda no implementada", Toast.LENGTH_LONG).show()
            }

            R.id.app_bar_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
