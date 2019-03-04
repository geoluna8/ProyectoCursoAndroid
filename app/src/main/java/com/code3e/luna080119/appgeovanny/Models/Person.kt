package com.code3e.luna080119.appgeovanny.Models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Person {
    var name : String = ""
    var phone : String = ""
    var address : String = ""
    var photo : Bitmap? = null

    constructor(name: String, phone: String, address: String, photo: Bitmap?) {
        this.name = name
        this.phone = phone
        this.address = address
        this.photo = photo
    }

    //Este constructor lo usaremos al momento de leer la BD
    constructor(name: String, phone: String, address: String, photo: ByteArray) {
        this.name = name
        this.phone = phone
        this.address = address
        this.photo = BitmapFactory.decodeByteArray(photo,0,photo.size)
    }

    //Para guardar la foto es necesario convertirla a binario (arreglo de bytes)

    fun getPhotoBytes() : ByteArray {
        val stream = ByteArrayOutputStream()
        photo?.compress(Bitmap.CompressFormat.PNG,100, stream)
        return stream.toByteArray()
    }

}