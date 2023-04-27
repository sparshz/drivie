package com.hindu.drivie.fragments.HomePage

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.R
import com.hindu.drivie.databinding.FragmentFragmentHomePageBinding
import com.hindu.drivie.databinding.FragmentHomeBinding
import com.hindu.drivie.model.DriverModel
import kotlinx.android.synthetic.main.activity_enter_dirver_detailsd.*
import kotlinx.android.synthetic.main.fragment_fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_fragment_home_page.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePage : Fragment() {

    private lateinit var viewModel: HomePageViewModel
    private var _binding:FragmentFragmentHomePageBinding? = null
    private val binding get() = _binding!!
    var checker = false

    private val dataMap=HashMap<String,Any>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        _binding = FragmentFragmentHomePageBinding.inflate(inflater,container,false)
        val root:View = binding.root

        fetchUser()


        //Floating Action Button
        root.createJob.setOnClickListener{
            root.create_job_dialogBox.visibility = View.VISIBLE
            root.createJob.visibility = View.GONE
        }


        //close dialog button
        root.close_dialogBox.setOnClickListener {
            root.create_job_dialogBox.visibility = View.GONE
            root.createJob.visibility = View.VISIBLE
            dataMap.clear()
            checker =false
        }

        //Create JOb button
        root.submit_job.setOnClickListener {
            createJob(root)
        }

        //Vehicle types
        val onClickListener = View.OnClickListener {view->
            when(view.id){
                R.id.vehicle_lmv->{
                    dataMap["lmv"] = true
                    vehicleType("lmv",dataMap,root.vehicle_lmv)
                }
                R.id.vehicle_muv->{
                    dataMap["muv"] = true
                    vehicleType("muv",dataMap,root.vehicle_muv)
                }
                R.id.vehicle_suv->{
                    dataMap["suv"] = true
                    vehicleType("suv",dataMap,root.vehicle_suv)
                }
                R.id.vehicle_sedan->{
                    dataMap["sedan"] = true
                    vehicleType("sedan",dataMap,root.vehicle_sedan)
                }
                R.id.vehicle_hatchback->{
                    dataMap["hatchback"] = true
                    vehicleType("hatchback",dataMap,root.vehicle_hatchback)
                }
            }
        }
        root.vehicle_lmv.setOnClickListener(onClickListener)
        root.vehicle_suv.setOnClickListener(onClickListener)
        root.vehicle_muv.setOnClickListener(onClickListener)
        root.vehicle_hatchback.setOnClickListener(onClickListener)
        root.vehicle_sedan.setOnClickListener(onClickListener)


        return root
    }


    private fun checkUser(callback:(Boolean)->Unit){
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().reference.child("Driver")
        val query = db.orderByKey().equalTo(uid)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    if (snapshot.hasChild(uid)){
                        callback.invoke(true)
                    }else{
                        callback.invoke(false)
                    }
                }else{
                    callback.invoke(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(false)
            }

        })
        query.keepSynced(true)
    }

    private fun fetchUser(){
        checkUser { isDriver->
            if (isDriver){
                createJob.visibility = View.GONE
                services_RV.visibility = View.VISIBLE
                drivers_RV.visibility = View.GONE
            }else{
                createJob.visibility = View.VISIBLE
                services_RV.visibility = View.GONE
                drivers_RV.visibility = View.VISIBLE
            }
        }
    }

    private fun createJob(root:View){
        val dbRef = FirebaseDatabase.getInstance().reference.child("Jobs")
        val currentUser = FirebaseAuth.getInstance().currentUser!!.uid
        when{
            jobTitle_ET.text.toString().isEmpty()->{
                Snackbar.make(root,"please enter the Job Title", Snackbar.LENGTH_SHORT).show()
            }

            jobDescription_ET.text.toString().isEmpty()->{
                Snackbar.make(root,"please enter the Job Description", Snackbar.LENGTH_SHORT).show()
            }

            jobLocation_ET.text.toString().isEmpty()->{
                Snackbar.make(root,"please enter the Job Location/Address", Snackbar.LENGTH_SHORT).show()
            }
            else->{

                val jobId = dbRef.push().key

                dataMap["jobTitle"] = jobTitle_ET.text.toString()
                dataMap["jobId"] = jobId.toString()
                dataMap["jobDescription"] = jobDescription_ET.text.toString()
                dataMap["jobLocation"] = jobLocation_ET.text.toString()
                dataMap["publisher"] = currentUser

                dbRef.child(jobId.toString()).updateChildren(dataMap)
                root.create_job_dialogBox.visibility = View.GONE
                root.createJob.visibility = View.VISIBLE
                Snackbar.make(root,"Job Add successfully", Snackbar.LENGTH_SHORT).show()
                dataMap.clear()

            }
        }
    }

    private fun vehicleType(text:String,hashMap: HashMap<String,Any>,background: CardView){

        if(!checker){
            checker=true
            background.setCardBackgroundColor(Color.parseColor("#FFB340"))
        }else{
            dataMap[text] = false
            checker=false
            background.setCardBackgroundColor(Color.parseColor("#fcecc0"))
        }
    }

}