package com.hindu.drivie.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.MainActivity
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

    override fun onStart() {
        super.onStart()
        val user : FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (FirebaseAuth.getInstance().currentUser != null){
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
        }
    }


}