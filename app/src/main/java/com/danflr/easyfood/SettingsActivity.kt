package com.danflr.easyfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.danflr.easyfood.helpers.PreferencesHelper
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val prefs = PreferencesHelper(this)

        cbDoableOnly.isChecked = prefs.showOnlyDoableRecipes

        cbDoableOnly.setOnCheckedChangeListener { buttonView, isChecked ->
            prefs.showOnlyDoableRecipes = isChecked
        }

        //lblServerAddress.text = prefs.currentAddress

        /*btnEditServerAddress.setOnClickListener {
            txtNewAddress.visibility = TextView.VISIBLE
            btnSave.visibility = Button.VISIBLE
        }

        btnSave.setOnClickListener {
            prefs.currentAddress = txtNewAddress.text.toString()
            txtNewAddress.visibility = TextView.INVISIBLE
            btnSave.visibility = Button.INVISIBLE
            lblServerAddress.text = prefs.currentAddress
        }*/
    }
}
