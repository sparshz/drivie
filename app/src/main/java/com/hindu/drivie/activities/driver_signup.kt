package com.hindu.drivie.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_driver_signup.*

class driver_signup : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_signup)

        back_rider_signup.setOnClickListener{
            startActivity(Intent(this,WelcomeActivity::class.java))
            finish()
        }

        have_account_driver.setOnClickListener{
            startActivity(Intent(this,driver_login::class.java))
            finish()
        }

        driverSignup.setOnClickListener {
            signUp()
        }


    }

    private fun signUp(){
        val driverName = fullName_Dirver.text.toString().trim{ it <= ' '}
        val driverEmail = email_driver.text.toString().trim{ it <= ' '}
        val password = password_direr.text.toString().trim{ it <= ' '}
        val phone = phone_drvier.text.toString().trim{ it <= ' '}

        when{
            TextUtils.isEmpty(driverName)->{
                Toast.makeText(this@driver_signup, "Name field cannot be empty", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(driverEmail)->{
                Toast.makeText(this@driver_signup, "Email field cannot empty", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(password)->{
                Toast.makeText(this@driver_signup, "Password field cannot be empty", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(phone)->{
                Toast.makeText(this@driver_signup, "Phone field cannot be empty", Toast.LENGTH_SHORT).show()
            }

            else->{
                val progressDialog = ProgressDialog(this@driver_signup)
                progressDialog.setTitle("Registration in progress")
                progressDialog.setTitle("Please wait")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(driverEmail,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            saveData(driverName,email_driver.toString(), phone, password, progressDialog)
                        }else{
                            val message = task.exception.toString()
                            Toast.makeText(this, "Some Error Occurred: $message", Toast.LENGTH_LONG).show()
                            println("Some Error Occurred: $message")
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }

        }

    }
    private fun saveData(fullName: String, email: String, phone: String, password: String,progressDialog:ProgressDialog){
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val database = Firebase.database
        val userRef = database.reference.child("Drivers")

        val dataMap = HashMap<String,Any>()
        dataMap["driverId"] = currentUserID
        dataMap["direrName"] = fullName
        dataMap["driverEmail"] = email
        dataMap["drivePassword"] = password
        dataMap["driverPhone"] = phone
        dataMap["driver"] = true

        userRef.child(currentUserID).setValue(dataMap).addOnCompleteListener { task->
            if (task.isSuccessful){
                progressDialog.dismiss()
                val intent = Intent(this@driver_signup, EnterDriverDetails::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }else{
                val message = task.exception.toString()
                Toast.makeText(this, "Some Error Occurred: $message", Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }

        }
    }
}