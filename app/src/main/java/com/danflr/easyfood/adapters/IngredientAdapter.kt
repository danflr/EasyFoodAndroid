package com.danflr.easyfood.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.android.volley.RequestQueue
import com.danflr.easyfood.R
import com.danflr.easyfood.RecipeDetailActivity
import com.danflr.easyfood.helpers.DBClient
import com.danflr.easyfood.models.Ingredient
import com.danflr.easyfood.models.Recipe
import com.google.android.material.snackbar.Snackbar

class IngredientAdapter(ingredients: ArrayList<Ingredient>, act: Activity, hideButton: Boolean) : BaseAdapter() {
    private var ingredients = ingredients
    val hide = hideButton
    private var inflater: LayoutInflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var callingActivity: Activity = act

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View { //Obtiene el elemento visual con los datos de cada cerrajero
        var v : View //Vista a retornar
        var viewHolder : ViewHolder //ViewHolder con la información necesaria para la vista
        v = inflater.inflate(R.layout.ingredient_list_item, null) //Infla la vista para poderla llenar y mostrar
        viewHolder = ViewHolder(v) //Obtiene el ViewHolder de la vista
        v.tag = viewHolder //Guarda ese viewHolder en la vista para usos futuros

        viewHolder.amount.text = ingredients.get(position).amount.toString()
        viewHolder.name.text = ingredients.get(position).ingredientName
        viewHolder.units.text = ingredients.get(position).unit

        viewHolder.btnAddToCart.setOnClickListener {
            //Guardar ingrediente en la lista de compra (SQLite)
            var db = DBClient(callingActivity, "Shopping", null, 1)
            var msg: String = ""
            if(db.addItem(ingredients.get(position))){
                msg = callingActivity.getString(R.string.actions_ingredient_added).replace("{ingredient}", ingredients.get(position).ingredientName)
            } else {
                msg = callingActivity.getString(R.string.unexpected_error)
            }
            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show()
        }

        if(hide) viewHolder.btnAddToCart.visibility = ImageButton.GONE

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
        var amount : TextView = view.findViewById(R.id.lblAmount)
        var units : TextView = view.findViewById(R.id.lblUnits)
        var btnAddToCart : ImageButton = view.findViewById(R.id.btnAddToCart)
    }
}