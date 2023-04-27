package com.hindu.drivie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.hindu.drivie.R
import com.hindu.drivie.model.JobModel

class MyJobAdapter(private val mContext:Context,
                   private val mJobs:List<JobModel>):RecyclerView.Adapter<MyJobAdapter.ViewHolder>() {

             inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView){
                 private val title: TextView = itemView.findViewById(R.id.jobTitle_MJ) as TextView
                 val count:TextView = itemView.findViewById(R.id.application_count) as TextView

                 fun bind(list:JobModel){
                     title.text = list.jobTitle
                 }

             }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.my_job_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mJobs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mJobs[position])
    }


}