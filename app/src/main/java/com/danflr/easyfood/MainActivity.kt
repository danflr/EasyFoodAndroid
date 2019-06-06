package com.danflr.easyfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.helpers.PreferencesHelper
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Login
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = PreferencesHelper(this)

        queue = Volley.newRequestQueue(this);

        if(prefs.isLoggedIn){
            startActivity(Intent(this, RecipesActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
            finish()
        }

        btnLogin.setOnClickListener {
            if(txtUsername.text.isNullOrEmpty() || txtPassword.text.isNullOrEmpty()){

            } else {
                val loginData = Login(txtUsername.text.toString(), txtPassword.text.toString());

                RequestHelper(this).logIn(loginData, queue, object : ResponseCallback {
                    override fun onSuccessResponse(response: JSONObject) {
                        if(!response.isNull("result") && response.getBoolean("result")){
                            Log.i("Authenticated!", "Logging in: " + loginData.username)
                            prefs.isLoggedIn = true;
                            prefs.username = txtUsername.text.toString();
                            prefs.password = txtPassword.text.toString();
                            prefs.showOnlyDoableRecipes = true;
                            prefs.userId = if(response.isNull("account_id")) "" else response.getString("account_id")
                            prefs.email = if(response.isNull("email")) "" else response.getString("email")
                            startActivity(Intent(applicationContext, RecipesActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, response.getString("reason"), Toast.LENGTH_LONG).show();
                        }
                    }

                    override fun onErrorResponse() {
                        prefs.isLoggedIn = false
                        prefs.username = ""
                        prefs.password = ""
                        prefs.userId = ""
                        prefs.email = ""
                    }
                })
            }
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

    }
}
