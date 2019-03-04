package com.code3e.luna080119.appgeovanny.Fragments


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.code3e.luna080119.appgeovanny.R
import com.code3e.luna080119.appgeovanny.WebActivity
import kotlinx.android.synthetic.main.fragment_about.*
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Detectamos click sobre los botones
        legalButton.setOnClickListener {
            val intent = Intent(activity, WebActivity::class.java)
            startActivity(intent)
        }

        whatsAppButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("whatsapp://send?phone=+525513561159&text=Hola!!"))
                startActivity(intent)
            } catch (e : Exception) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.whatsapp"))
                startActivity(intent)
            }
        }

        emailButton.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "victor.haro@code3e.com", null))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Prueba de correo")
                intent.putExtra(Intent.EXTRA_TEXT, "Saludos...")
                startActivity(Intent.createChooser(intent,"Enviar correo"))
            }catch (e : Exception){
                Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        finishButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(activity)
            alertDialog.setTitle("Alerta!")
            alertDialog.setMessage("Quieres cerrar sesion?")

            alertDialog.setPositiveButton("Si quiero", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    //Borramos el token de preferencias
                    val sharedPreferences = activity!!.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().commit()

                    activity?.finish()
                }
            })

            alertDialog.setNegativeButton("No quiero", null)
            alertDialog.show()
        }

    }

}
