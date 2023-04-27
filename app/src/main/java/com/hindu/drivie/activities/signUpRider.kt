package com.hindu.drivie.activities

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.hindu.drivie.MainActivity
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_sign_up_rider.*

class signUpRider : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_rider)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        back.setOnClickListener{
            startActivity(Intent(this,WelcomeActivity::class.java))
        }

        have_account.setOnClickListener{
            startActivity(Intent(this,rider_login::class.java))
        }

        create_rider_account.setOnClickListener{
            riderSignup()
        }
    }

    private fun riderSignup(){
        val name = rider_name.text.toString().trim{ it <= ' '}
        val email = rider_email.text.toString().trim{ it <= ' '}
        val password = rider_password.text.toString().trim{ it <= ' '}
        val phone = rider_phone.text.toString().trim{ it <= ' '}

        when{
            TextUtils.isEmpty(name)->{
                Toast.makeText(this,"please enter the name", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(email)->{
                Toast.makeText(this,"please enter the email", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(password)->{
                Toast.makeText(this,"please enter the password", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(phone)->{
                Toast.makeText(this,"please enter the phone number", Toast.LENGTH_SHORT).show()
            }

            else->{
                val progressDialog = ProgressDialog(this@signUpRider)
                progressDialog.setTitle("Registration in progress")
                progressDialog.setTitle("Please wait")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            saveData(name,email, phone, password, progressDialog)
                        }else{
                            val message = task.exception.toString()
                            Toast.makeText(this, "Some Error Occurred: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    private fun saveData(name: String, email: String, phone: String, password: String, progressDialog: ProgressDialog) {

        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val database = FirebaseDatabase.getInstance().reference.child("Riders")

        val dataMap= HashMap<String,Any>()

        dataMap["riderEmail"] = email
        dataMap["riderName"] = name
        dataMap["riderPhone"] = phone
        dataMap["riderPassword"] = password
        dataMap["riderId"] = currentUserID
        database.child(currentUserID).setValue(dataMap).addOnCompleteListener {task->
            if (task.isSuccessful){
                progressDialog.dismiss()
                val intent = Intent(this@signUpRider, MainActivity::class.java)
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
