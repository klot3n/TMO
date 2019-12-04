package com.gmail.klot3n.tmo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.View

class TMODatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME="tmo.db"
        private val DATABASE_VERSION=1
        const val TABLE_NAME="tmo_DB"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "TIME TEXT, "
                + "DESCRIPTION TEXT, "
                + "RATING INTEGER) "
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun addData(db:SQLiteDatabase , name: String, time: String,
        description: String, rating:Int) {
        val values = ContentValues()
        values.put("NAME", name)
        values.put("TIME", time)
        values.put("DESCRIPTION", description)
        values.put("RATING", rating)
        db.insert(TABLE_NAME, null, values)
    }
}