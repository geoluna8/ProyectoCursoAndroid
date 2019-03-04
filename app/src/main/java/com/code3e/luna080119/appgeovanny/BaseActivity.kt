package com.code3e.luna080119.appgeovanny

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun showSessionAlert(message : String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alerta!")
        alertDialog.setMessage(message)

        alertDialog.setPositiveButton("Si quiero", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                finish()
            }
        })

        alertDialog.setNegativeButton("No quiero", null)
        alertDialog.show()
    }
}
