package com.code3e.luna080119.appgeovanny.Models

import org.json.JSONObject

class Car {
    var brand : String = ""
    var model : String = ""
    var image : Int = 0
    var url : String = ""

    constructor(brand: String, model: String, image: Int) {
        this.brand = brand
        this.model = model
        this.image = image
    }

    //Creamos un constructor que reciba el JSON de la nube
    constructor(jsonObject: JSONObject){
        this.brand = jsonObject.getString("Brand")
        this.model = jsonObject.getString("Model")
        this.url = jsonObject.getString("Thumbnail")
    }
}