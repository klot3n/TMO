package com.gmail.klot3n.tmo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gmail.klot3n.tmo.TMODatabaseHelper.Companion.TABLE_NAME
import kotlinx.android.synthetic.main.activity_archive_detail.*
import kotlinx.android.synthetic.main.activity_begin_process.*

class ArchiveDetailActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive_detail)

        val nameItem=intent.getStringExtra("nameItem")

        var cursor = TMODatabaseHelper(this).readableDatabase.
            query("tmo_DB", arrayOf("_id", "NAME","TIME","DESCRIPTION","RATING","CNT_HI_RATING"),"NAME = ?",
                arrayOf(nameItem), null, null, "_id ASC")

        var expArch=""
        while (cursor.moveToNext()){
            expArch += cursor.getString(3) + "\nОценка: "+cursor.getInt(4).toString() + "\n"
        }
        cursor.moveToFirst()
        name_arch.text=cursor.getString(1)
        time_arch.text=cursor.getString(2)
        exp_arch.text=expArch

        resume_but_arch.setOnClickListener {
            cursor.moveToLast()
            val i = Intent(this, ProcessActivity::class.java)
            i.putExtra("progress", cursor.getInt(4))
            i.putExtra("name_exp", cursor.getString(1))
            i.putExtra("time_exp", cursor.getString(2))
            i.putExtra("countHiRating", cursor.getInt(5))
            startActivity(i)
            cursor.close()
        }

        delete_but_arch.setOnClickListener {
            val db=TMODatabaseHelper(this).writableDatabase
            db.delete(TABLE_NAME, "NAME = ?", arrayOf(nameItem))
            db.close()
            cursor.close()
            val i = Intent(this, ArchiveListActivity::class.java)
            startActivity(i)
        }

    }
}