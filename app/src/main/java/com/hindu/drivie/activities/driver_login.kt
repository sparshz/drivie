package com.hindu.drivie.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.MainActivity
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_driver_login.*
import java.util.Objects

class driver_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_login)

        login_back_driver.setOnClickListener{
            startActivity(Intent(this,driver_signup::class.java))
        }
        driverSignInBtn.setOnClickListener{
            signIn()
        }
    }

    private fun signIn() {
        val email = driverEmail_signIn.text.toString()
        val password = driverPassword_signIn.text.toString()

        when {
            TextUtils.isEmpty(email.trim { it <= ' ' }) -> {
                Toast.makeText(this@driver_login, "Please enter your email", Toast.LENGTH_SHORT)
                    .show()
            }
            TextUtils.isEmpty(password.trim { it <= ' ' }) -> {
                Toast.makeText(this@driver_login, "Please enter your Password", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                val progressDialog = ProgressDialog(this@driver_login)
                progressDialog.setMessage("Signing In")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user= FirebaseDatabase.getInstance().reference.child("DriverDetails")
                            user.addValueEventListener(object :ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.hasChild(FirebaseAuth.getInstance().currentUser!!.uid)){
                                        val intent = Intent(this@driver_login, MainActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        progressDialog.dismiss()
                                    }else{
                                        val intent = Intent(this@driver_login, EnterDriverDetails::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        progressDialog.dismiss()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })
                                progressDialog.dismiss()
                        } else {
                            val message = task.exception.toString()

                            Toast.makeText(this@driver_login, message, Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }

        }
    }

}