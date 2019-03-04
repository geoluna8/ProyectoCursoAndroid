package com.code3e.luna080119.appgeovanny.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.code3e.luna080119.appgeovanny.Models.Person
import java.lang.Exception

//Clase para manejar la BD

class DBManager : SQLiteOpenHelper {

    constructor(context: Context?) : super(context, "mibase.sqlite", null,1){

    }


    //Este metodo nos ayuda a crea el esquema de base de datos
    //Por ejemplo, aqui administramos las tablas
    override fun onCreate(db: SQLiteDatabase?) {
        val query =  "CREATE TABLE IF NOT EXISTS CONTACTS (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME STRING, ADDRESS STRING, PHONE STRING, PHOTO BLOB);"
        db?.execSQL(query)
    }

    // Este metodo lo usamos cuando necesitamos cambios en el esquema de base de datos
    //oldVersion es la version antigua de base de datos
    //newVersion es la version nueva de base de datos

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    //Funcion para guardar en la BD
    fun guardarPersona(person : Person) : Boolean {

        try {
            val contentValues = ContentValues()
            contentValues.put("NAME",person.name)
            contentValues.put("ADDRESS",person.address)
            contentValues.put("PHONE",person.phone)
            contentValues.put("PHOTO",person.getPhotoBytes())
            //Para guardar usamos el atributo writetableDatabase
            val id = writableDatabase.insert("CONTACTS",null,contentValues)
            if (id.toInt() == -1){
                return false
            }else{return true}
        }catch (e : Exception){return false}
    }

    //Funcion buscar persona
    fun buscarPersona(name : String) : Person?{
        val cursor : Cursor = readableDatabase.rawQuery("SELECT * FROM CONTACTS WHERE NAME LIKE" + "'%" + name + "%'", null)
        //Cursor es donde regresa los resultados de la busqueda
        if(cursor.count>0){
            cursor.moveToFirst()
            val pName = cursor.getString(cursor.getColumnIndex("NAME"))
            val pAddress = cursor.getString(cursor.getColumnIndex("ADDRESS"))
            val pPhone = cursor.getString(cursor.getColumnIndex("PHONE"))
            val pPhoto = cursor.getBlob(cursor.getColumnIndex("PHOTO"))

            val person = Person(pName, pPhone, pAddress, pPhoto)
            return person
        }else{
            return null
        }
    }

}