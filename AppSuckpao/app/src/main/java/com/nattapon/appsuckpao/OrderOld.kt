package com.nattapon.appsuckpao

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nattapon.appsuckpao.Adapter.OrderOldAdapter
import com.nattapon.appsuckpao.Data.OrderOlds


class OrderOld : AppCompatActivity() {

    lateinit var OrderOldList:MutableList<OrderOlds>
    lateinit var ref2: DatabaseReference
    lateinit var listView2: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


        OrderOldList= mutableListOf()
        val uid = FirebaseAuth.getInstance().uid ?: ""

        ref2 = FirebaseDatabase.getInstance().getReference("orderold/$uid")
        listView2=findViewById(R.id.orderlistold)


        ref2.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){


                    for(h in p0.children){
                        val order = h.getValue(OrderOlds::class.java)
                        OrderOldList.add(order!!)
                        Log.d("OrderOld","Get oldOrder")
                    }
                    val adapter = OrderOldAdapter(this@OrderOld,R.layout.orderold,OrderOldList)
                    listView2.adapter=adapter

                }
            }

        })


    }
}