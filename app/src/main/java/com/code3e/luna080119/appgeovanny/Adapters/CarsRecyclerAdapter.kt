package com.code3e.luna080119.appgeovanny.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.code3e.luna080119.appgeovanny.Models.Car
import com.code3e.luna080119.appgeovanny.R
import kotlinx.android.synthetic.main.car_layout.view.*

//Esta clase define el comportamiento del RecyclerView

class CarsRecyclerAdapter : RecyclerView.Adapter<MyViewHolder> {

    var carsArray : ArrayList<Car> = ArrayList()
    var context: Context? = null

    constructor(context : Context, carsArray: ArrayList<Car>){
        this.context = context
        this.carsArray = carsArray
    }

    //Este método define cuantas listas tendra el RecyclerView
    override fun getItemCount(): Int {
        return carsArray.size
    }

    //Este método crea la vista a utilizar en cada fila del RecyclerView
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view : View = View.inflate(context, R.layout.car_layout, null)
        return MyViewHolder(view)
    }

    //Este método coloca los datos sobre cada fila del Recycler
    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        //Con ayuda de la posición (p1) pasamos datos
        val car : Car = carsArray.get(p1)
        //p0.holderView!!.carImage.setImageResource(car.image)
        //Usamos la libreria de Glide para descargar asicronamente la imagen de internet
        Glide.with(context!!).load(car.url).into(p0!!.holderView!!.carImage)
        p0.holderView!!.carBrand.setText(car.brand)
        p0.holderView!!.carModel.setText(car.model)
    }
}

//Esta clase ayuda a crear el Cache de la vista asociada a la fila de RecyclerView

class MyViewHolder : RecyclerView.ViewHolder{
    var holderView : View? = null
    constructor(view: View) : super(view){
        holderView = view
    }
}