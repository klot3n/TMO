package com.gmail.klot3n.tmo

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_archive_list.*

class ArchiveListActivity: AppCompatActivity(){
    lateinit var db: SQLiteDatabase
    lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive_list)

        db = TMODatabaseHelper(this).readableDatabase

        cursor = db.query("tmo_DB", arrayOf("_id", "NAME"),
                null, null, "NAME", null, null)

        list_db.adapter = SimpleCursorAdapter(
            this, android.R.layout.simple_list_item_1,
            cursor, arrayOf("NAME"), intArrayOf(android.R.id.text1), 0
        )

        list_db.setOnItemClickListener { parent, view, position, id ->
            val nameItem= (list_db.getItemAtPosition(position) as Cursor)
                .getString(1)
            val i = Intent(this, ArchiveDetailActivity::class.java)
            i.putExtra("nameItem", nameItem)
            startActivity(i)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cursor.close()
        db.close()
    }
}