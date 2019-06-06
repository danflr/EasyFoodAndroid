package com.danflr.easyfood.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.danflr.easyfood.R
import com.danflr.easyfood.models.Comment
import com.danflr.easyfood.models.RecipeStep

class StepAdapter(steps: ArrayList<RecipeStep>, act: Activity) : BaseAdapter() {
    private var steps = steps
    private var inflater: LayoutInflater = act.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var callingActivity: Activity = act

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View { //Obtiene el elemento visual con los datos de cada cerrajero
        var v : View //Vista a retornar
        var viewHolder : ViewHolder //ViewHolder con la información necesaria para la vista
        v = inflater.inflate(R.layout.step_list_item, null) //Infla la vista para poderla llenar y mostrar
        viewHolder = ViewHolder(v) //Obtiene el ViewHolder de la vista
        v.tag = viewHolder //Guarda ese viewHolder en la vista para usos futuros

        viewHolder.step.text = steps.get(position).step.toString()
        viewHolder.content.text = steps.get(position).content.toString()

        return v //Retorna la vista
    }

    override fun getItem(position: Int): RecipeStep {
        return steps.get(position) //Retorna el cerrajero de la posición solicitada
    }

    override fun getItemId(position: Int): Long {
        return position.toLong() //Retorna el id de un objeto
    }

    override fun getCount(): Int {
        return this.steps.size //Retorna la cantidad de cerrajeros asociados al cliente
    }

    fun getItems() : ArrayList<RecipeStep> {
        return this.steps
    }

    //ViewHolder personalizado para nuestro elemento
    class ViewHolder(view: View) {
        var step : TextView = view.findViewById(R.id.lblStep)
        var content : TextView = view.findViewById(R.id.lblContent)
    }
}