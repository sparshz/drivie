package com.hindu.drivie.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hindu.drivie.fragments.HomePage.HomePage
import com.hindu.drivie.fragments.JobPage.JobPage

class HomeTabs(fm:FragmentManager, lifecycle:Lifecycle):FragmentStateAdapter(fm,lifecycle) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                HomePage()
            }
            1->{
                JobPage()
            }
            else->{
                HomePage()
            }
        }
    }




}