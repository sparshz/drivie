package com.hindu.drivie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_driver_login.*

class driver_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_login)

        login_back_driver.setOnClickListener{
            startActivity(Intent(this,driver_signup::class.java))
        }
    }
}