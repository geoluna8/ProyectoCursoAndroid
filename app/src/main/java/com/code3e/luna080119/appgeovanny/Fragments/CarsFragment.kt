package com.code3e.luna080119.appgeovanny.Fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.code3e.luna080119.appgeovanny.Adapters.CarsRecyclerAdapter
import com.code3e.luna080119.appgeovanny.Models.Car

import com.code3e.luna080119.appgeovanny.R
import com.code3e.luna080119.appgeovanny.TabsActivity
import kotlinx.android.synthetic.main.fragment_cars.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CarsFragment : Fragment(), Response.Listener<String>, Response.ErrorListener {

    //Este método define el Layout que utilizará el fragmento

    val carsArray : ArrayList<Car> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cars, container, false)
    }

    //Este método es el recomendado para iniciar variables y funciones

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Iniciamos un Arreglo de Autos
        //val carsArray : ArrayList<Car> = ArrayList()
        //carsArray.add(Car("Chevrolet","Volt",R.drawable.chevy_volt))
        //carsArray.add(Car("Toyota","Venza",R.drawable.toyota_venza))
        //carsArray.add(Car("Mini","Cooper",R.drawable.mini_clubman))
        //carsArray.add(Car("Volvo","s60",R.drawable.volvo_s60))
        //carsArray.add(Car("Chevrolet","Fortwo",R.drawable.smart_fortwo))

        //carsRecyclerView.layoutManager = GridLayoutManager(activity, 1,LinearLayoutManager.HORIZONTAL, false)


        val url = "https://autossaltillo.com/testing/top.php?"
        val method = Request.Method.GET
        //com.android.volley.Request
        val request : StringRequest = StringRequest(method, url, this, this)

        Volley.newRequestQueue(activity).add(request)


    }


    //Esta funcion se ejecuta cuando el server responde a la app
    override fun onResponse(response: String?) {
        println("Response: " + response)
        //Procesamos la respuesta con la clase JSON
        try{
            val json : JSONObject = JSONObject(response)
            val code : Int = json.getInt("code")
            val message : String = json.getString("message")
            if (code == 0){//Si es Cero, el user y pass fueron correctos
                //Leemos la llave data que es donde viene un arreglo de autos
                val data : JSONArray = json.getJSONArray("data")
                //Iteramos (for) el arreglo para modelar autos
                for (i in 0..(data.length()-1)){
                    val carJson : JSONObject = data.getJSONObject(i)
                    val car : Car = Car(carJson)
                    carsArray.add(car)
                }
                //Iniciamos el Adapatador y lo vinculamos al RecyclerView
                val adapter : CarsRecyclerAdapter = CarsRecyclerAdapter(activity!!, carsArray)
                carsRecyclerView.adapter = adapter

                //Configuramos si el Recycler es Lineal o Grid
                carsRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(activity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    //Esta funcion se ejecuta si ocurre un error en la conexion
    override fun onErrorResponse(error: VolleyError?) {
        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
    }


}