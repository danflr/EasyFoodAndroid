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
import com.danflr.easyfood.models.Profile
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(this)

        val username: String = intent.getStringExtra("username")
        val act = this

        RequestHelper(this).getProfileFromUsername(username, queue, object: ResponseCallback {
            override fun onSuccessResponse(response: JSONObject) {
                val profileData = Profile(response)
                lblUsername.text = profileData.username
                lvRecipes.adapter = RecipesAdapter(profileData.recipes, act, queue)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var inflater = menuInflater
        inflater.inflate(R.menu.bottom_drawer_menu, menu)
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
