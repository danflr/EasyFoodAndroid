package com.danflr.easyfood.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.R
import com.danflr.easyfood.helpers.DBClient
import com.danflr.easyfood.helpers.PreferencesHelper
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Ingredient
import com.danflr.easyfood.models.UserIngredient
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class AddIngredientAdapter(ingredients: ArrayList<Ingredient>, act: Activity) : BaseAdapter() {
    private var ingredients = ingredients
    private var inflater: LayoutInflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var callingActivity: Activity = act

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View { //Obtiene el elemento visual con los datos de cada cerrajero
        var v : View //Vista a retornar
        var viewHolder : ViewHolder //ViewHolder con la información necesaria para la vista
        v = inflater.inflate(R.layout.addingredient_list_item, null) //Infla la vista para poderla llenar y mostrar
        viewHolder = ViewHolder(v) //Obtiene el ViewHolder de la vista
        v.tag = viewHolder //Guarda ese viewHolder en la vista para usos futuros

        viewHolder.amount.text = callingActivity.getString(R.string.labels_amount) + ": " + ingredients.get(position).amount.toString()
        viewHolder.name.text = ingredients.get(position).ingredientName
        viewHolder.btnAdd.setOnClickListener {
            if(!viewHolder.txtAmount.text.equals("0")){
                val ingredient = UserIngredient(PreferencesHelper(callingActivity).userId, ingredients.get(position).ingredientId, Integer.parseInt(viewHolder.txtAmount.text.toString()))
                RequestHelper(callingActivity).addUserIngredient(ingredient, Volley.newRequestQueue(callingActivity), object: ResponseCallback{
                    override fun onSuccessResponse(response: JSONObject) {
                        Toast.makeText(callingActivity, response.getString("message"), Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        return v //Retorna la vista
    }

    override fun getItem(position: Int): Ingredient {
        return ingredients.get(position) //Retorna el cerrajero de la posición solicitada
    }

    override fun getItemId(position: Int): Long {
        return position.toLong() //Retorna el id de un objeto
    }

    override fun getCount(): Int {
        return this.ingredients.size //Retorna la cantidad de cerrajeros asociados al cliente
    }

    fun getItems() : ArrayList<Ingredient> {
        return this.ingredients
    }

    //ViewHolder personalizado para nuestro elemento
    class ViewHolder(view: View) {
        var name : TextView = view.findViewById(R.id.lblIngredientName)
        var amount : TextView = view.findViewById(R.id.lblIngredientCategory)
        var btnAdd : MaterialButton = view.findViewById(R.id.btnAdd)
        var txtAmount : EditText = view.findViewById<EditText>(R.id.txtAmount)
    }
}