package com.danflr.easyfood.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.R
import com.danflr.easyfood.helpers.DBClient
import com.danflr.easyfood.helpers.PreferencesHelper
import com.danflr.easyfood.helpers.RequestHelper
import com.danflr.easyfood.interfaces.ItemTap
import com.danflr.easyfood.interfaces.ResponseCallback
import com.danflr.easyfood.models.Ingredient
import com.danflr.easyfood.models.UserIngredient
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class ShoppingListAdapter(ingredients: ArrayList<Ingredient>, act: Activity, itemTap: ItemTap) : BaseAdapter() {
    private var ingredients = ingredients
    private var inflater: LayoutInflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var callingActivity: Activity = act
    val tap = itemTap

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View { //Obtiene el elemento visual con los datos de cada cerrajero
        var v : View //Vista a retornar
        var viewHolder : ViewHolder //ViewHolder con la información necesaria para la vista
        v = inflater.inflate(R.layout.ingredient_sl_item, null) //Infla la vista para poderla llenar y mostrar
        viewHolder = ViewHolder(v) //Obtiene el ViewHolder de la vista
        v.tag = viewHolder //Guarda ese viewHolder en la vista para usos futuros

        viewHolder.amount.text = ingredients.get(position).amount.toString()
        viewHolder.name.text = ingredients.get(position).ingredientName
        viewHolder.category.text = ingredients.get(position).categoryName

        viewHolder.btnMarkBought.setOnClickListener {
            //Guardar ingrediente en la lista de compra (SQLite)
            var db = DBClient(callingActivity, "Shopping", null, 1)
            var msg: String = ""
            if(db.removeItem(ingredients.get(position))){
                val ingredient = UserIngredient(PreferencesHelper(callingActivity).userId, ingredients.get(position).ingredientId, Integer.parseInt(viewHolder.amount.text.toString()))

                RequestHelper(callingActivity).addUserIngredient(ingredient, Volley.newRequestQueue(callingActivity), object:
                    ResponseCallback {
                    override fun onSuccessResponse(response: JSONObject) {
                        Toast.makeText(callingActivity, "Ingrediente agregado", Toast.LENGTH_SHORT).show()
                    }
                })
                msg = callingActivity.getString(R.string.actions_ingredient_removed).replace("{ingredient}", ingredients.get(position).ingredientName)
            } else {
                msg = callingActivity.getString(R.string.unexpected_error)
            }
            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show()
            tap.onTap(ingredients.get(position).ingredientName)
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
        var name : TextView = view.findViewById(R.id.lblName)
        var amount : TextView = view.findViewById(R.id.lblAmount)
        var category: TextView = view.findViewById(R.id.lblCategory)
        var btnMarkBought : ImageButton = view.findViewById(R.id.btnMarkBought)
    }
}