package com.hindu.drivie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_driver_signup.*

class driver_signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_signup)


        back_rider_signup.setOnClickListener{
            startActivity(Intent(this,WelcomeActivity::class.java))
        }

        have_account_driver.setOnClickListener{
            startActivity(Intent(this,driver_login::class.java))
        }
    }
}