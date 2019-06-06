package com.danflr.easyfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Account
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        queue = Volley.newRequestQueue(this)

        btnRegister.setOnClickListener {
            var valid = true
            registerProgress.visibility = ProgressBar.VISIBLE
            if(txtEmail.text.isNullOrEmpty() || !txtEmail.text.toString().equals(txtConfirmEmail.text.toString())) valid = false
            if(txtUsername.text.isNullOrEmpty() || !txtUsername.text.toString().equals(txtConfirmUsername.text.toString())) valid = false
            if(txtPassword.text.isNullOrEmpty() || !txtPassword.text.toString().equals(txtConfirmPassword.text.toString())) valid = false
            if(valid){
                val account = Account(txtUsername.text.toString(), txtPassword.text.toString(), txtEmail.text.toString())

                RequestHelper(this).register(account, queue, object : ResponseCallback {
                    override fun onSuccessResponse(response: JSONObject) {
                        Toast.makeText(applicationContext, if(response.has("reason")) response.getString("reason") else "Account created", Toast.LENGTH_LONG).show();
                        registerProgress.visibility = ProgressBar.GONE
                        finish()
                    }

                    override fun onErrorResponse(errorMessage: String) {
                        registerProgress.visibility = ProgressBar.GONE
                    }
                })
            }
        }

    }

}
