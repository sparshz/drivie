package com.hindu.drivie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_rider_login.*

class rider_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider_login)

        login_back.setOnClickListener{
            startActivity(Intent(this,signUpRider::class.java))
        }


    }
}