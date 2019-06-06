package com.danflr.easyfood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.danflr.easyfood.helpers.PreferencesHelper
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_bottomdrawer_dialog.*
import kotlinx.android.synthetic.main.fragment_bottomdrawer_list_dialog.*
import kotlinx.android.synthetic.main.fragment_bottomdrawer_dialog.view.*

class BottomDrawerDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_bottomdrawer_dialog, container, false)
        val prefs = PreferencesHelper(this.context!!)

        v.findViewById<TextView>(R.id.lblUsername).text = prefs.username
        v.findViewById<TextView>(R.id.lblEmail).text = prefs.email

        v.findViewById<NavigationView>(R.id.nav_view).setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawer_logout -> {
                    prefs.email = ""
                    prefs.userId = ""
                    prefs.showOnlyDoableRecipes = true
                    prefs.password = ""
                    prefs.username = ""
                    prefs.isLoggedIn = false
                    startActivity(Intent(this.context!!, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }

                R.id.drawer_recipes -> {
                    startActivity(Intent(this.context!!, RecipesActivity::class.java))
                }

                R.id.drawer_shopping_list -> {
                    startActivity(Intent(this.context!!, ShoppingListActivity::class.java))
                }

                R.id.drawer_profile -> {
                    val i = Intent(this.context!!, ProfileActivity::class.java)
                    i.putExtra("username", PreferencesHelper(this.context!!).username)
                    startActivity(i)
                }

                R.id.drawer_all_ingredients -> {
                    startActivity(Intent(this.context!!, IngredientsActivity::class.java))
                }

                R.id.drawer_user_ingredients -> {
                    startActivity(Intent(this.context!!, UserIngredientsActivity::class.java))
                }

                else -> {
                    Toast.makeText(this.context!!, "Not implemented", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }

        return v
    }

}
