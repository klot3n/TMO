package com.gmail.klot3n.tmo

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_begin_process.*
import kotlin.properties.Delegates

class BeginProcessActivity : AppCompatActivity() {

    var progressRating = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin_process)

        rating_exp.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rating_view.text = "Оценка: ${progress - 10}"
                progressRating = progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

        })

        ok.setOnClickListener {

            //проверка на повтор имени в Database
            var name=name_exp.text.toString()
            var num = 0
            var cursor = TMODatabaseHelper(this).readableDatabase.
                query("tmo_DB", arrayOf("_id", "NAME"),"NAME = ?",
                    arrayOf(name_exp.text.toString()), null, null, null)
            while (cursor.moveToFirst()) {
                name = name_exp.text.toString()+"_${++num}"
                cursor=TMODatabaseHelper(this).readableDatabase.
                    query("tmo_DB", arrayOf("NAME"), "NAME=?", arrayOf(name), null, null, null)
            }
            cursor.close()

            //запись в Database
            val db = TMODatabaseHelper(this)
            db.addData(
                db.writableDatabase, name, time_exp.text.toString(),
                "  " + location_exp.text.toString() + "\n  "
                        + participant_exp.text.toString() + "\n  "
                        + description_exp.text.toString(),
                        progressRating-10
            )
            db.close()


                val i = Intent(this, ProcessActivity::class.java)
                i.putExtra("progress", progressRating)
                i.putExtra("name_exp", name)
                i.putExtra("time_exp", time_exp.text.toString())
                startActivity(i)
            }
        }

    }