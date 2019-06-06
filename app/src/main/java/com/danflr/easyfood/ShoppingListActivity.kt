package com.danflr.easyfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.danflr.easyfood.adapters.ShoppingListAdapter
import com.danflr.easyfood.helpers.DBClient
import com.danflr.easyfood.interfaces.ItemTap
import kotlinx.android.synthetic.main.activity_shopping_list.*

class ShoppingListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        refresher.isRefreshing = true

        val ingredients = DBClient(this, "Shopping", null, 1).getItems()

        val act = this

        lvShoppingList.adapter = ShoppingListAdapter(ingredients, this, object: ItemTap {
            override fun onTap(itemId: String) {
                refresher.isRefreshing = true
                lvShoppingList.adapter = ShoppingListAdapter(ingredients, act, this)
                refresher.isRefreshing = false
            }
        })

        refresher.isRefreshing = false

        refresher.setOnRefreshListener {
            lvShoppingList.adapter = ShoppingListAdapter(ingredients, this, object: ItemTap {
                override fun onTap(itemId: String) {
                    refresher.isRefreshing = true
                    lvShoppingList.adapter = ShoppingListAdapter(ingredients, act, this)
                    refresher.isRefreshing = false
                }
            })
        }

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
