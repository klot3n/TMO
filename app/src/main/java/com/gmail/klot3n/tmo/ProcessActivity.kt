package com.gmail.klot3n.tmo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_begin_process.*
import kotlinx.android.synthetic.main.activity_process.*
import kotlinx.android.synthetic.main.activity_process.rating_exp
import kotlinx.android.synthetic.main.activity_process.rating_view

class ProcessActivity : AppCompatActivity() {

    var progressRating: Int? = null//intent.getIntExtra("progress",213)
    var countHighRating = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process)

        countHighRating = intent.getIntExtra("countHiRating",0)
        this.progressRating = intent.getIntExtra("progress", 10)
        name_exp_filled.text = intent.getStringExtra("name_exp")
        time_exp_filled.text = intent.getStringExtra("time_exp")
        rating_exp.progress = progressRating!!.toInt()
        rating_view.text = "Оценка: ${intent.getIntExtra("progress", 10) - 10}"
        when(countHighRating){
            0 ->  title_modify_exp.text = getString(R.string.modify_exp)
            1 -> title_modify_exp.text = getString(R.string.first_test_modify_exp)
            2 -> title_modify_exp.text = getString(R.string.second_test_modify_exp)
        }

        rating_exp.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rating_view.text = "Оценка: ${progress - 10}"
                progressRating = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

        })

        oki.setOnClickListener {
            val emptyEditText: Editable? = null
            when {

                progressRating!! < 20 -> {
                    countHighRating = 0
                    addDataToTable()
                    modify_exp.text = emptyEditText
                    description_new_exp.text = Editable.Factory.getInstance().newEditable("") //Это как вариант
                    title_modify_exp.text = getString(R.string.modify_exp)
                }
                progressRating == 20 && countHighRating == 0 -> {
                    ++countHighRating
                    addDataToTable()
                    modify_exp.text = emptyEditText
                    description_new_exp.text = emptyEditText
                    title_modify_exp.text = getString(R.string.first_test_modify_exp)
                }

                progressRating == 20 && countHighRating == 1 -> {
                    ++countHighRating
                    addDataToTable()
                    modify_exp.text = emptyEditText
                    description_new_exp.text = emptyEditText
                    title_modify_exp.text = getString(R.string.second_test_modify_exp)
                }

                progressRating == 20 && countHighRating > 1 -> {
                    addDataToTable()
                    val i = Intent(this, EndProcessActivity::class.java)
                    i.putExtra("name_exp_filled", name_exp_filled.text.toString())
                    i.putExtra("time_exp_filled", time_exp_filled.text.toString())
                    i.putExtra("modify_exp", modify_exp.text.toString())
                    i.putExtra("description_new_exp", description_new_exp.text.toString())
                    startActivity(i)
                }

            }

        }

        main_menu_2.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    fun addDataToTable() {
        val db = TMODatabaseHelper(this)
        db.addData(
            db.writableDatabase, name_exp_filled.text.toString(), time_exp_filled.text.toString(),
            "  " + modify_exp.text.toString() + "\n  "
                    + description_new_exp.text.toString(),
            this.progressRating!! - 10,
            countHighRating
        )
        db.close()
    }
}