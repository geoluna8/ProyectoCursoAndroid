package com.code3e.luna080119.appgeovanny

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer
import com.code3e.luna080119.appgeovanny.Adapters.TabsAdapter
import kotlinx.android.synthetic.main.activity_tabs.*
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.tab_layout.view.*

class TabsActivity : BaseActivity(), SensorEventListener {
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    //En este metodo nos da datos del acelerometro
    override fun onSensorChanged(event: SensorEvent?) {
         val values = event!!.values
        println("X: " + values[0] + " Y: " + values[1] + " Z: " + values[2])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabs)

        //Creamos el adaptador y lo asociamos al ViewPager
        val adapter = TabsAdapter(supportFragmentManager)
        tabsViewPager.adapter = adapter

        //Asociamos el TabLayout con el ViewPager
        tabLayout.setupWithViewPager(tabsViewPager)

        //Configuramos los iconos de cada Tab
        val tabView = View.inflate(this,R.layout.tab_layout, null)
        tabView.tabIcon.setImageResource(R.drawable.ic_tab_car)
        //tabView.tabIcon.setBackgroundColor(Color.RED)
        tabLayout.getTabAt(0)?.setCustomView(tabView)

        val tabView1 = View.inflate(this,R.layout.tab_layout, null)
        tabView1.tabIcon.setImageResource(R.drawable.ic_tab_maps)
        //tabView1.tabIcon.setBackgroundColor(Color.GREEN)
        tabLayout.getTabAt(1)?.setCustomView(tabView1)

        val tabView2 = View.inflate(this,R.layout.tab_layout, null)
        tabView2.tabIcon.setImageResource(R.drawable.ic_tab_contacts)
        //tabView2.tabIcon.setBackgroundColor(Color.YELLOW)
        tabLayout.getTabAt(2)?.setCustomView(tabView2)

        val tabView3 = View.inflate(this,R.layout.tab_layout, null)
        tabView3.tabIcon.setImageResource(R.drawable.ic_tab_settings)
        //tabView3.tabIcon.setBackgroundColor(Color.BLUE)
        tabLayout.getTabAt(3)?.setCustomView(tabView3)

        //Configuramos una animacion
        tabsViewPager.setPageTransformer(false,CubeOutTransformer())

        checkSensors()

    }


    //Funcion para ver los sensores del telefono
    fun checkSensors(){
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in sensors){
            println("Name: " + sensor.name + " - Vendor: " + sensor.vendor)
        }

        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, 100000)

        //Para detener el sensor, borrar para dejarlo funcionar
        sensorManager.unregisterListener(this)

    }


    //sin super evita regresar presionando back
    override fun onBackPressed() {
        //super.onBackPressed()

        showSessionAlert("Quieres cerrar sesión")
    }


}
