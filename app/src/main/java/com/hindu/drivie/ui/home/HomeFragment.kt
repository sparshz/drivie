package com.hindu.drivie.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.R
import com.hindu.drivie.adapter.HomeTabs
import com.hindu.drivie.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    var pageTitle =""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.tabs_layout)
        viewPager = view.findViewById(R.id.viewPager)

        CoroutineScope(Dispatchers.IO).launch {
            fetchUser { pageTitle->
                val adapter = HomeTabs(childFragmentManager,lifecycle)
                viewPager.adapter = adapter

                TabLayoutMediator(tabLayout,viewPager){tab,position->
                    when(position){
                        0->{
                            tab.text = "Home"
                        }
                        1->{
                            tab.text = pageTitle
                        }
                    }
                }.attach()
            }
        }
    }


    private fun checkUser(callback:(Boolean)->Unit){
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseDatabase.getInstance().reference.child("Drivers")
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
    private fun fetchUser(callback: (String) -> Unit){

        CoroutineScope(Dispatchers.IO).launch{
            checkUser { isDriver->
                if (isDriver){
                    callback.invoke("Job Requests")

                }else{
                    callback.invoke("My Jobs")
                }
            }
        }

    }



}