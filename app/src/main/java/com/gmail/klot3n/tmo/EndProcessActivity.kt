package com.gmail.klot3n.tmo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_end_process.*
import kotlinx.android.synthetic.main.activity_process.*

class EndProcessActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_process)

        end_name_exp_filled.text=intent.getStringExtra("name_exp_filled")
        end_time_exp_filled.text=intent.getStringExtra("time_exp_filled")
        end_modify_exp.text=intent.getStringExtra("modify_exp")
        end_description_new_exp.text=intent.getStringExtra("description_new_exp")

        buton_back.setOnClickListener {
            onBackPressed()
        }

        buton_end.setOnClickListener {
            val i_end =Intent(this,MainActivity::class.java)
            startActivity(i_end)
        }


    }

}