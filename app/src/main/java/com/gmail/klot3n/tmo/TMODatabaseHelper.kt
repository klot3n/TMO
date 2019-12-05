package com.gmail.klot3n.tmo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TMODatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {
    companion object{
        private val DATABASE_NAME="tmo.db"
        private val DATABASE_VERSION=2
        const val TABLE_NAME="tmo_DB"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        updateDB(db, 0, DATABASE_VERSION)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        updateDB(db, oldVersion, newVersion)
    }

    private fun updateDB(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1 && db != null) {
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                        + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME TEXT, "
                        + "TIME TEXT, "
                        + "DESCRIPTION TEXT, "
                        + "RATING INTEGER, "
                        + "CNT_HI_RATING INTEGER) "
            )
        }
        if (oldVersion == 2 && db != null) db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN CNT_HI_RATING INTEGER")
    }

    fun addData(db:SQLiteDatabase , name: String, time: String,
        description: String, rating:Int, cntHiRating:Int) {
        val values = ContentValues()
        values.put("NAME", name)
        values.put("TIME", time)
        values.put("DESCRIPTION", description)
        values.put("RATING", rating)
        values.put("CNT_HI_RATING", cntHiRating)
        db.insert(TABLE_NAME, null, values)
    }
}