package com.danflr.easyfood.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.android.volley.RequestQueue
import com.danflr.easyfood.ProfileActivity
import com.danflr.easyfood.R
import com.danflr.easyfood.RecipeDetailActivity
import com.danflr.easyfood.models.Recipe

class RecipesAdapter(recipes: ArrayList<Recipe>, act: Activity, queue: RequestQueue) : BaseAdapter() {
    private var recipes = recipes
    private var inflater: LayoutInflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var callingActivity: Activity = act
    private var queue = queue

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View { //Obtiene el elemento visual con los datos de cada cerrajero
        var v : View //Vista a retornar
        var viewHolder : ViewHolder //ViewHolder con la información necesaria para la vista
        v = inflater.inflate(R.layout.recipe_list_item, null) //Infla la vista para poderla llenar y mostrar
        viewHolder = ViewHolder(v) //Obtiene el ViewHolder de la vista
        v.tag = viewHolder //Guarda ese viewHolder en la vista para usos futuros
        viewHolder.name.text = recipes.get(position).recipe_Name //Establece el nombre del cerrajero
        viewHolder.category.text = recipes.get(position).category
        viewHolder.user.text = recipes.get(position).user
        viewHolder.likes.text = recipes.get(position).likes.toString()
        viewHolder.comments.text = recipes.get(position).comments.toString()
        viewHolder.date.text = recipes.get(position).postDate
        if(recipes.get(position).getImageBitmap() != null) {
            viewHolder.pic.setImageBitmap(recipes.get(position).getImageBitmap())
            viewHolder.pic.scaleType = ImageView.ScaleType.FIT_XY
        }

        viewHolder.item.setOnClickListener {
            //Implementar intent para lanzar la actividad de detalle de la receta con su ID
            val i = Intent(callingActivity, RecipeDetailActivity::class.java)
            i.putExtra("RecipeID", recipes.get(position).recipe_Id)
            callingActivity.startActivity(i)
        }

        viewHolder.user.setOnClickListener {
            val i = Intent(callingActivity, ProfileActivity::class.java)
            i.putExtra("username", recipes.get(position).user)
            callingActivity.startActivity(i)
        }

        return v //Retorna la vista
    }

    override fun getItem(position: Int): Recipe {
        return recipes.get(position) //Retorna el cerrajero de la posición solicitada
    }

    override fun getItemId(position: Int): Long {
        return position.toLong() //Retorna el id de un objeto
    }

    override fun getCount(): Int {
        return this.recipes.size //Retorna la cantidad de cerrajeros asociados al cliente
    }

    fun getItems() : ArrayList<Recipe> {
        return this.recipes
    }

    //ViewHolder personalizado para nuestro elemento
    class ViewHolder(view: View) {
        var name : TextView = view.findViewById(R.id.lblRecipeName)
        var category : TextView = view.findViewById(R.id.lblCategory)
        var user: TextView = view.findViewById(R.id.lblUser)
        var date: TextView = view.findViewById(R.id.lblPostDate)
        var likes: Button = view.findViewById(R.id.btnLikes)
        var comments: Button = view.findViewById(R.id.btnComments)
        var item: ConstraintLayout = view.findViewById(R.id.recipeContainer)
        var pic: ImageView = view.findViewById(R.id.recipeImg)
    }

}