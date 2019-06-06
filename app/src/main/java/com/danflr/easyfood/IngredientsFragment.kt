package com.danflr.easyfood


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.android.volley.toolbox.Volley
import com.danflr.easyfood.adapters.IngredientAdapter
import com.danflr.easyfood.models.Ingredient
import org.json.JSONArray
import org.json.JSONObject

private const val ARG_PARAM1 = "Ingredients"

class IngredientsFragment() : Fragment() {
    // TODO: Rename and change types of parameters
    private var ingredientsJson: String = ""
    private var ingredientList: ArrayList<Ingredient>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ingredientsJson = it.getString(ARG_PARAM1)
        }

        ingredientList = ArrayList<Ingredient>()

        if(ingredientsJson!!.length > 0){
            var ingredients = JSONArray(ingredientsJson)
            for(i in 0 .. ingredients.length() - 1){
                ingredientList!!.add(Ingredient(ingredients.getJSONObject(i)))
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_ingredients, container, false)
        val lv = v.findViewById<ListView>(R.id.lvIngredients)
        ingredientList = ArrayList<Ingredient>()

        if(ingredientsJson!!.length > 0){
            var ingredients = JSONArray(ingredientsJson)
            for(i in 0 .. ingredients.length() - 1){
                ingredientList!!.add(Ingredient(ingredients.getJSONObject(i)))
            }
        }
        lv.adapter = IngredientAdapter(ingredientList!!, this.activity!!, false)
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putString("Ingredients", param1)
                }
            }
    }

}
