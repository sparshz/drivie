package com.hindu.drivie.fragments.JobPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hindu.drivie.R
import com.hindu.drivie.adapter.MyJobAdapter
import com.hindu.drivie.databinding.FragmentJobPageBinding
import com.hindu.drivie.model.JobModel
import kotlinx.android.synthetic.main.fragment_job_page.view.*

class JobPage : Fragment() {
    private lateinit var viewModel: JobPageViewModel
    private var _binding:FragmentJobPageBinding? = null
    private val binding get() = _binding!!

    var recyclerView:RecyclerView? = null
    private var myJobAdapter:MyJobAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(JobPageViewModel::class.java)
        _binding =FragmentJobPageBinding.inflate(inflater,container,false)
        val root:View = binding.root

        viewModel.loadJobModel!!.observe(viewLifecycleOwner, Observer {
            initView(root)
            root.myJob_RV.visibility = View.VISIBLE
            myJobAdapter = context?.let { it1-> MyJobAdapter(it1, it as List<JobModel>) }
            recyclerView!!.adapter = myJobAdapter
            myJobAdapter!!.notifyDataSetChanged()

        })

        return root
    }

    private fun initView(root:View){
        recyclerView = root.findViewById(R.id.myJob_RV) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.isNestedScrollingEnabled = false
        val llM = LinearLayoutManager(context)
        llM.reverseLayout = true
        llM.stackFromEnd = true
        recyclerView!!.layoutManager = llM
    }


}