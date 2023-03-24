package com.hindu.drivie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        ridersignup.setOnClickListener{
            val intent = Intent(this, signUpRider::class.java)
            startActivity(intent)
        }

        driversignup.setOnClickListener{
            startActivity(Intent(this,driver_signup::class.java))
        }
    }
}