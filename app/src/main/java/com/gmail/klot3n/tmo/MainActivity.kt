package com.gmail.klot3n.tmo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //удалить таблицу
//        var db=TMODatabaseHelper(this).writableDatabase
//        db.execSQL("DROP TABLE IF EXISTS tmoDB")

        new_process.setOnClickListener {
            val i =Intent(this,BeginProcessActivity::class.java)
            startActivity(i)
        }

        archive_process.setOnClickListener {
            val i =Intent(this,ArchiveListActivity::class.java)
            startActivity(i)
        }

    }
}
