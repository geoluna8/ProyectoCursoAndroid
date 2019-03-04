package com.code3e.luna080119.appgeovanny

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MotionEvent
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject
import java.lang.Exception

//Implementamos las interfaces de Volley para que nos notifique cuando la conexion es exitosa o fallida (Listener)

class LoginActivity : AppCompatActivity(), Response.Listener<String>, Response.ErrorListener {

    var playerSuccess : MediaPlayer? = null
    var playerError : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Iniciamos los Players con los archivos locales
        playerSuccess = MediaPlayer.create(this,R.raw.tada)
        playerError = MediaPlayer.create(this,R.raw.error)

        loginButton.setOnClickListener(){
           loginPressed()
        }

        emailEditText.setText("victor@code3e.com")
        passEditText.setText("android")

        //Revisamos si el token ya esta guardado en preferencias
        if(loadToken() !=""){
            val intent = Intent(this,TabsActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        //Cargamos el video mp4 en la vista VideoView
        val uri = "android.resource://" + packageName + "/" + R.raw.moments
        //val uri = "http://media.heywatch.com.s3.amazonaws.com/hls/big_buck_bunny/big_buck_bunny.m3u8"
        videoView.setVideoURI(Uri.parse(uri))
        videoView.start()

        videoView.setOnCompletionListener {
            videoView.start()
        }

        //animate() es un atributo para animar cualquier vista
        loginButton.animate().alpha(0.0f).setDuration(1000).withEndAction{
            loginButton.animate().alpha(1.0f).setDuration(1000)
        }

        loginButton.animate().scaleX(1f).setDuration(5000)
        loginButton.animate().scaleY(1f).setDuration(5000)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        println("X: " + event!!.x + " - Y: " + event!!.y)

        return super.onTouchEvent(event)
    }

    fun loginPressed(){
        val email = emailEditText.text.toString()
        val pass = passEditText.text.toString()

        var errorMessage = ""

        if(pass == ""){
            errorMessage = "Escribe tu contraseña"
        }
        if(email == "") {
            errorMessage = "Escribe tu correo electronico"
        }
        if(errorMessage != ""){
            playerError?.start()
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }else {
            //val intent = Intent(this, TabsActivity::class.java)
            //startActivity(intent)


            val url = "https://autossaltillo.com/testing/login.php?" + "Email=" + email + "&Password=" + pass
            val method = Request.Method.GET
            //com.android.volley.Request
            val request : StringRequest = StringRequest(method, url, this, this)

            Volley.newRequestQueue(this).add(request)

        }
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

                playerSuccess?.start()

                val data : JSONObject = json.getJSONObject("data")
                val sessionToken : String = data.getString("sessionToken")
                saveToken(sessionToken)

                val intent = Intent(this, TabsActivity::class.java)
                startActivity(intent)
            } else {playerError?.start()
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            playerError?.start()
        }
    }

    //Esta funcion se ejecuta si ocurre un error en la conexion
    override fun onErrorResponse(error: VolleyError?) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        playerError?.start()
    }

    //Funcion para guardar el token de sesion en preferencias
    fun saveToken(sessionToken : String){
        val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", sessionToken)
        editor.commit()
    }

    //Funcion para leer el token de sesion de preferencias
    fun loadToken(): String{
        val sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")
        return token
    }

}
