package com.code3e.luna080119.appgeovanny.Fragments


import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.code3e.luna080119.appgeovanny.Database.DBManager
import com.code3e.luna080119.appgeovanny.Models.Person

import com.code3e.luna080119.appgeovanny.R
import kotlinx.android.synthetic.main.fragment_contacts.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ContactsFragment : Fragment() {

    var bitmap : Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Tomar foto
        personImage.setOnClickListener{
            checkCameraPermissions()
        }
        //Guardar persona
        saveButton.setOnClickListener {
            //Leemos los campos de texto y validamos
            val name = personName.text.toString()
            val address = personAddress.text.toString()
            var phone = personPhone.text.toString()

            if(name!="" && address!="" && phone!="" && bitmap != null){
                //Guardamos en la BD
                val person = Person(name, address, phone, bitmap)
                val dbManager = DBManager(activity)
                if (dbManager.guardarPersona(person)){
                    Toast.makeText(activity, "Guardado exitosamente", Toast.LENGTH_SHORT).show()
                    personName.setText("")
                    personAddress.setText("")
                    personPhone.setText("")
                    bitmap = null
                }else{
                    Toast.makeText(activity, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity, "Ingresa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        //Buscar persona
        searchButton.setOnClickListener {
            val name = personName.text.toString()
            if (name != ""){
               val dbManager = DBManager(activity)
               val person : Person? = dbManager.buscarPersona(name)
                if (person != null){
                    personName.setText(person!!.name)
                    personAddress.setText(person!!.address)
                    personPhone.setText(person!!.phone)
                    personImage.setImageBitmap(person!!.photo)
                }else{
                    Toast.makeText(activity, "No se encontro la persona", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "Ingresa un texto", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //Funcion para permisos de la camara

    fun checkCameraPermissions(){
        if (ActivityCompat.checkSelfPermission(activity!!, android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 2000)
            //startActivityForResult significa que esperamos un resultado, en este caso es la foto
        }else{
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),3000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        bitmap = data?.extras?.get("data") as Bitmap
        personImage.setImageBitmap(bitmap)

    }

}
