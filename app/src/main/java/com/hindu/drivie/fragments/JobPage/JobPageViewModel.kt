package com.hindu.drivie.fragments.JobPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.callback.IJobCallback
import com.hindu.drivie.model.JobModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class JobPageViewModel : ViewModel(), IJobCallback {
    private var jobLiveData:MutableLiveData<List<JobModel>>? = null

    private val jobCallback:IJobCallback = this
    private var messageError:MutableLiveData<String>? = null

    val loadJobModel:MutableLiveData<List<JobModel>>?
    get() {
        if (jobLiveData == null){
            jobLiveData = MutableLiveData()
            messageError = MutableLiveData()
            viewModelScope.launch(Dispatchers.IO) {
                loadJobs()
            }
        }
        return jobLiveData
    }

    private fun loadJobs() {
        val jobList = ArrayList<JobModel>()
        val dbRef = FirebaseDatabase.getInstance().reference.child("Jobs")
        dbRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                jobList.clear()
                for (snapshot in snapshot.children){
                    val jobModel = snapshot.getValue<JobModel>(JobModel::class.java)
                    if (jobModel!!.publisher == FirebaseAuth.getInstance().currentUser!!.uid){
                        jobList.add(jobModel)
                    }
                    jobCallback.onJobLoadSuccess(jobList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                jobCallback.onJobLoadFailed(error.message)
            }

        })
    }

    override fun onJobLoadFailed(str: String) {
        val mutableLiveData = messageError
        mutableLiveData!!.value = str
    }

    override fun onJobLoadSuccess(list: List<JobModel>) {
        val mutableLiveData = jobLiveData
        mutableLiveData!!.value = list
    }
}