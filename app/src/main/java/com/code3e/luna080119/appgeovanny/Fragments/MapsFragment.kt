package com.code3e.luna080119.appgeovanny.Fragments


import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.code3e.luna080119.appgeovanny.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import java.lang.Exception
import java.util.jar.Manifest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

//Implementamos la interfaz OnMapReady para saber cuando el mapa este listo
//Implementamos la intefaz LocationListener para detectar los cambios en la ubicacion
class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener {

    var myGoogleMap: GoogleMap? = null
    var locationManager : LocationManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Accesamos al fragmento "mapsFragment"
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapsFragment) as SupportMapFragment
        //Solicitamos el google map de forma Asincrona
        mapFragment.getMapAsync(this)
    }

    //Implementamos el metodo onMapReady para saber cuando el mapa esta listo

    override fun onMapReady(p0: GoogleMap?) {
        myGoogleMap = p0
        //myGoogleMap?.isTrafficEnabled = true
        //myGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE

        val metro = LatLng(19.401984, -99.170461)
        myGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(metro, 12f))

        val marker =
            MarkerOptions().position(metro).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi)).rotation(45f)
        myGoogleMap?.addMarker(marker)

        val polyline = PolylineOptions()
        polyline.add(LatLng(19.401984, -99.170461))
        polyline.add(LatLng(19.402014, -99.170729))
        polyline.add(LatLng(19.403456, -99.171110))
        polyline.add(LatLng(19.405101, -99.176228))

        polyline.color(Color.BLACK)
        polyline.width(10f)

        myGoogleMap?.addPolyline(polyline)

        //Detectar click sobre el mapa
        myGoogleMap?.setOnMapClickListener {
            val marker = MarkerOptions().position(it).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi))
            myGoogleMap?.addMarker(marker)

        }

        //Listener detectar cuando el usuario termina de mover el mapa
        myGoogleMap?.setOnCameraIdleListener{
            //Lee las coordenadas del centro del mapa
            val coords = myGoogleMap!!.cameraPosition.target
            addressTextView.setText("Calculando direccion")
            //Hacemos Geocoding (convertir coordenadas a una direccion)
            val geocoder = Geocoder(activity)
            try{
                val addresses = geocoder.getFromLocation(coords.latitude, coords.longitude,1)
                if(addresses != null && addresses.size > 0){
                    val addressLine=addresses.get(0).getAddressLine(0)
                    addressTextView.setText(addressLine)
                } else{
                    addressTextView.setText("No se encontraron direcciones")
                }
            }catch (e: Exception){
                addressTextView.setText("Error al encontrar dirección")
            }
        }

        checkLocationPermissions()
        checkUserLocation()
    }

    //Funcion para detectar cambios en la ubicacion del usuario
    fun checkUserLocation(){
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Iniciamos el servicio de ubicación
            locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0.0f, this)
            //locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,0.0f, this)
            Log.d("App","Iniciando rastreo...")
    }}

    //Funcion para revisar los permisos de ubicación
    fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            myGoogleMap?.isMyLocationEnabled = true
        } else {
            //si no ha aceptado los permisos, los solicitamos
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                2019)
        }
    }

    //Funcion para detectar si el usuario acepto o denego los permisos de ubicacion
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2019){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                checkLocationPermissions()
                checkUserLocation()
            }else{
                Toast.makeText(activity!!, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        Log.d("App","Lat: " + location!!.latitude)
        Log.d("App","Long: " + location!!.longitude)
        Log.d("App","Velocidad: " + location!!.speed)
        Log.d("App","Grados: " + location!!.bearing)
        Log.d("App","Precision: " + location!!.accuracy)
        Log.d("App","Altitud: " + location!!.altitude)
        Log.d("App","Fecha: " + location!!.time)
        println("Lat: " + location!!.latitude.toString())
        println("Velocidad: " + location!!.speed.toString())
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }


}