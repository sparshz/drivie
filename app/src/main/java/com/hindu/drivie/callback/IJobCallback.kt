package com.hindu.drivie.callback

import com.hindu.drivie.model.JobModel

interface IJobCallback {
    fun onJobLoadFailed(str:String)
    fun onJobLoadSuccess(list:List<JobModel>)
}