package com.hindu.drivie.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.MainActivity
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_driver_login.*
import kotlinx.android.synthetic.main.activity_rider_login.*

class rider_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rider_login)

        login_back.setOnClickListener{
            startActivity(Intent(this,signUpRider::class.java))
        }
        rider_login.setOnClickListener{
            signIn()
        }
    }
    private fun signIn(){
        val email = riderEmail_login.text.toString()
        val password = riderPassword_signin.text.toString()

        when {
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(this@rider_login, "Please enter your email", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(password.trim { it <= ' ' }) -> {
                Toast.makeText(this@rider_login, "Please enter your Password", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val progressDialog = ProgressDialog(this@rider_login)
                progressDialog.setMessage("Signing In")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@rider_login, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            progressDialog.dismiss()
                        } else {
                            val message = task.exception.toString()

                            Toast.makeText(this@rider_login, message, Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }

        }
    }

}