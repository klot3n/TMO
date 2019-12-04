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
            kotlin.run {
                val db = TMODatabaseHelper(this).writableDatabase
                val values = ContentValues()
                values.put("NAME", name_exp.toString())
                values.put("TIME", time_exp.toString())
                values.put("DESCRIPTION", location_exp.toString()+"\n"
                           +participant_exp.toString()+"\n"
                           +description_exp.toString())
                values.put("RATING", progressRating)
                db.insert(TMODatabaseHelper.TABLE_NAME, null, values)
                db.close()
            }
                val i = Intent(this, ProcessActivity::class.java)
                i.putExtra("progress", progressRating)
                i.putExtra("name_exp", name_exp.text.toString())
                i.putExtra("time_exp", time_exp.text.toString())
                startActivity(i)
            }
        }

    }