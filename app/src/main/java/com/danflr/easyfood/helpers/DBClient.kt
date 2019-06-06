package com.danflr.easyfood.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.danflr.easyfood.models.Ingredient

class DBClient (ctx: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(ctx, name, factory, version) {
    var ctx: Context = ctx
    var name: String = name
    var factory: SQLiteDatabase.CursorFactory? = factory
    var version: Int = version

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE ShoppingList (IngredientName TEXT, Category TEXT, Unit TEXT, Amount INTEGER, IngredientID TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ShoppingList")
        db!!.execSQL("CREATE TABLE ShoppingList (IngredientName TEXT, Category TEXT, Unit TEXT, Amount INTEGER, IngredientID TEXT)")
    }

    fun addItem(item: Ingredient) : Boolean {
        val db = this.writableDatabase
        try {
            val values = ContentValues()
            values.put("IngredientName", item.ingredientName)
            values.put("Category", item.categoryName)
            values.put("Unit", item.unit)
            values.put("Amount", item.amount)
            values.put("IngredientID", item.ingredientId)

            if (getItem(item.ingredientName).ingredientName.length == 0) {
                db.insert("ShoppingList", null, values)
                Log.i("Adding item", item.ingredientName)
            } else {
                db.update("ShoppingList", values, "IngredientID = '" + item.ingredientId + "'", null)
            }
            return true
        } catch (ex: SQLiteException) {
            Toast.makeText(ctx, ex.message, Toast.LENGTH_LONG).show()
            Log.e("Adding item error", ex.message)
            return false
        }
    }

    fun getItems() : ArrayList<Ingredient> {
        val items = ArrayList<Ingredient>()
        val db = this.writableDatabase

        try {
            val itemQuery = "SELECT * FROM ShoppingList"

            val cursor = db.rawQuery(itemQuery, null)

            if(cursor.moveToFirst()){
                do {
                    items.add(Ingredient(cursor))
                } while(cursor.moveToNext())
            }
            cursor.close()
        } catch(ex: SQLiteException){
            Log.e("Getting items error", ex.message)
            Toast.makeText(ctx, ex.message, Toast.LENGTH_LONG).show()

        }

        return items
    }

    fun getItem(name: String) : Ingredient {
        var item: Ingredient = Ingredient("", "", "", 0, "", "", "")
        try {
            val itemQuery = "SELECT * FROM ShoppingList WHERE IngredientName = '" + name + "'"

            val db = this.writableDatabase
            val cursor = db.rawQuery(itemQuery, null)

            if(cursor.moveToFirst()){
                do {
                    item = Ingredient(cursor)
                } while(cursor.moveToNext())
            }
            cursor.close()
        } catch(ex: SQLiteException){
            Log.e("Searching item" + name, ex.message)
            Toast.makeText(ctx, ex.message, Toast.LENGTH_LONG).show()
        }
        return item
    }

    fun itemExists(item: Ingredient) : Boolean {
        val db = this.writableDatabase
        try {
            val itemQuery = "SELECT * FROM ShoppingList WHERE IngredientName = '" + item.ingredientName + "'"

            val cursor = db.rawQuery(itemQuery, null)
            val res = cursor.moveToFirst()
            cursor.close()
            return res
        } catch(ex: SQLiteException){
            Log.e("Checking item", ex.message)
            return false
        }
    }

    fun removeItem(item: Ingredient) : Boolean {
        val db = this.writableDatabase
        try {
            val deleteQuery = "DELETE FROM ShoppingList WHERE IngredientName = '" + item.ingredientName + "' AND Amount = " + item.amount.toString()
            db.execSQL(deleteQuery)
            return true
        } catch(ex: SQLiteException) {
            return false
        }
    }

    fun clearData() {
        val db = this.writableDatabase
        try {
            val clearQuery = "DELETE FROM ShoppingList"
            db.execSQL(clearQuery)
        } catch(ex: SQLiteException){
            Log.e("Couldn't clear db", "An error occurred while deleting from ShoppingList")
        }
    }

}