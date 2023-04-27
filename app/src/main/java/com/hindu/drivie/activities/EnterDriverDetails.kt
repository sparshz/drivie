package com.hindu.drivie.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.hindu.drivie.MainActivity
import com.hindu.drivie.R
import kotlinx.android.synthetic.main.activity_enter_dirver_detailsd.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class EnterDriverDetails : AppCompatActivity() {
    var checker = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_dirver_detailsd)

        lmv.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vehicleType(lmv_txt, lmv)
            }

        }
        muv.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vehicleType(muv_txt, muv)
            }
        }

        suv.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vehicleType(suv_txt, suv)
            }
        }
        hatchback.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vehicleType(hatchback_txt, hatchback)
            }
        }
        sedan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                vehicleType(sedan_txt,sedan)
            }
        }

        submit_btn_driverDetails.setOnClickListener {view->
            saveData(view)
        }
    }

    private fun saveData(view: View){
        val dbRef = FirebaseDatabase.getInstance().reference.child("DriverDetails")

        when{
            driverAdhaar.text.toString().isEmpty()->{
                Snackbar.make(view,"please enter your Adhaar...", Snackbar.LENGTH_SHORT).show()
            }
            dlNumber_Dirver.text.toString().isEmpty()->{
                Snackbar.make(view,"please enter your DL Number...", Snackbar.LENGTH_SHORT).show()
            }
            experience_et.text.toString().isEmpty()->{
                Snackbar.make(view,"please enter your Experience...", Snackbar.LENGTH_SHORT).show()
            }
            age_driver_et.text.toString().isEmpty()->{
                Snackbar.make(view,"Age field cannont be empty", Snackbar.LENGTH_SHORT).show()
            }

            else->{
                val dataMap = HashMap<String,Any>()
                dataMap["adNumber"] = driverAdhaar.text.toString()
                dataMap["dlNumber"] = dlNumber_Dirver.text.toString()
                dataMap["drivingExp"] = experience_et.text.toString()
                dataMap["driverAge"] = age_driver_et.text.toString()

                dbRef.child(FirebaseAuth.getInstance().currentUser!!.uid).updateChildren(dataMap)
                startActivity(Intent(this,MainActivity::class.java))
            }
        }



    }

    private fun vehicleType(text:TextView, background:CardView){

        if(!checker){
            checker=true
            background.setCardBackgroundColor(Color.parseColor("#FFB340"))
            CoroutineScope(Dispatchers.IO).launch {
                addVehicleType(text,background)
            }
        }else{
            checker=false
            background.setCardBackgroundColor(Color.parseColor("#fcecc0"))
            CoroutineScope(Dispatchers.IO).launch {
                removeVehicle(text,background)
            }
        }
    }

    private fun addVehicleType(text:TextView, background:CardView){
        FirebaseDatabase.getInstance().reference.child("VehicleType")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(text.text.toString()).setValue(true)
    }

    private fun removeVehicle(text:TextView, background:CardView){
        FirebaseDatabase.getInstance().reference.child("VehicleType")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(text.text.toString()).removeValue()
    }


}