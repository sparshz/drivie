package com.hindu.drivie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView.RecyclerListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hindu.drivie.R
import com.hindu.drivie.model.DriverModel
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class ViewDiverAdapter(private val mContext:Context,
                       private val mDriver:List<DriverModel>):RecyclerView.Adapter<ViewDiverAdapter.ViewHolder>() {


                           inner class ViewHolder(@NonNull itemView: View):RecyclerView.ViewHolder(itemView){
                               val driverName: TextView = itemView.findViewById(R.id.driverName_item) as TextView
                               val driverImage:CircleImageView = itemView.findViewById(R.id.driverProfileIV) as CircleImageView
                               val address:TextView = itemView.findViewById(R.id.driverAddress_item) as TextView
                               val age:TextView = itemView.findViewById(R.id.driverAge_item) as TextView
                               val experience:TextView = itemView.findViewById(R.id.driverExp_item) as TextView
                               val hire_button:AppCompatButton = itemView.findViewById(R.id.hireDriver_itemBtn) as AppCompatButton
                               val lmv:CardView = itemView.findViewById(R.id.item_lmv)
                               val muv:CardView = itemView.findViewById(R.id.item_lmv)
                               val suv:CardView = itemView.findViewById(R.id.item_lmv)
                               val sedan:CardView = itemView.findViewById(R.id.item_lmv)
                               val hatchback:CardView = itemView.findViewById(R.id.item_lmv)
                               val vehicleTypeLL1:LinearLayout = itemView.findViewById(R.id.vehicleType_item)
                               val vehicleTypeLL2:LinearLayout = itemView.findViewById(R.id.vehicleType_item2)

                               fun bind(list:DriverModel){
                                   driverName.text = list.direrName
                                   address.text = list.driverAddress
                                   age.text = list.driverAge
                                   experience.text = list.drivingExp
                                   Glide.with(mContext).load(list.driverProfile).into(driverImage)
                               }
                           }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.driver_item_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDriver.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDriver[position])
        val drive = mDriver[position]
    }

    private fun getVehicles(lmv:CardView,
                            suv:CardView,
                            muv:CardView,
                            hatchback:CardView,
                            sedan:CardView,
                            driverId:String){
        val dbRef = FirebaseDatabase.getInstance().reference.child("Drivers").child(driverId)

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data = snapshot.getValue(DriverModel::class.java)
                    if (data!!.lmv){

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}