package com.danflr.easyfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.adapters.CommentAdapter
import com.danflr.easyfood.adapters.IngredientAdapter
import com.danflr.easyfood.adapters.StepAdapter
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Recipe
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_recipe_detail.*
import org.json.JSONObject

class RecipeDetailActivity : AppCompatActivity() {

    lateinit var recipe: Recipe
    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        recipeLoad.visibility = ProgressBar.VISIBLE

        var recipeID: String = ""
        queue = Volley.newRequestQueue(applicationContext)
        if(intent.getStringExtra("RecipeID") != null){
            recipeID = intent.getStringExtra("RecipeID")
        }

        fab_add.setOnClickListener {
            Snackbar.make(fab_add, R.string.error_not_implemented, Snackbar.LENGTH_LONG).show()
        }

        val act = this

        if(recipeID.length > 0){
            RequestHelper(this).getFullRecipe(recipeID, queue, object: ResponseCallback {
                override fun onSuccessResponse(response: JSONObject) {
                    recipe = Recipe(response)
                    lblRecipeName.text = recipe.recipe_Name
                    lblRecipeCategory.text = recipe.category
                    btnComments.text = recipe.comments.toString()
                    btnLikes.text = recipe.likes.toString()
                    recipeLoad.visibility = ProgressBar.GONE
                    lvItems.adapter = IngredientAdapter(recipe.ingredients, act, false)
                }

                override fun onErrorResponse() {
                    recipeLoad.visibility = ProgressBar.GONE
                    finish();
                }
            })
        }

        recipeTabs.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.text){
                    getString(R.string.tabs_ingredients) -> {
                        lvItems.adapter = IngredientAdapter(recipe.ingredients, act, false)
                    }

                    getString(R.string.tabs_comments) -> {
                        lvItems.adapter = CommentAdapter(recipe.commentsList, act)
                    }

                    getString(R.string.tabs_instructions) -> {
                        lvItems.adapter = StepAdapter(recipe.steps, act)
                    }

                    else -> Toast.makeText(applicationContext, "Not implemented", Toast.LENGTH_SHORT).show()
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
                startActivity(Intent(this, SettingsActivity::class.java))
            }

        }
        return super.onOptionsItemSelected(item)
    }
}
