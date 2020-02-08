package com.nattapon.appsuckpao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nattapon.appsuckpao.Adapter.OrderAdapter
import com.nattapon.appsuckpao.Data.Order

class Order : AppCompatActivity() {

    lateinit var OrderList:MutableList<Order>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        OrderList= mutableListOf()
        val uid = FirebaseAuth.getInstance().uid ?: ""

        ref = FirebaseDatabase.getInstance().getReference("order/$uid")
        listView=findViewById(R.id.orderlist)


        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){

                    for(h in p0.children){
                        val order = h.getValue(Order::class.java)
                        OrderList.add(order!!)
                    }
                    val adapter = OrderAdapter(applicationContext,R.layout.orders,OrderList)
                    listView.adapter=adapter

                }
            }

        })
    }
}
