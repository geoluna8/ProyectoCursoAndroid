package com.code3e.luna080119.appgeovanny

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(timerTask {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)

            finish()

        }, 3000)
    }
}
