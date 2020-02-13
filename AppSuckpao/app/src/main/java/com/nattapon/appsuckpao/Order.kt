package com.nattapon.appsuckpao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nattapon.appsuckpao.Adapter.OrderAdapter
import com.nattapon.appsuckpao.Adapter.OrderOldAdapter
import com.nattapon.appsuckpao.Data.Order
import com.nattapon.appsuckpao.Data.OrderOlds
import kotlinx.android.synthetic.main.orders.*

class Order : AppCompatActivity() {

    lateinit var OrderList:MutableList<Order>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView

    lateinit var OrderOldList:MutableList<OrderOlds>
    lateinit var ref2: DatabaseReference
    lateinit var listView2: ListView

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

        OrderOldList= mutableListOf()


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
                    val adapter = OrderOldAdapter(applicationContext,R.layout.orderold,OrderOldList)
                    listView2.adapter=adapter

                }
            }

        })


        }
    override fun onOptionsItemSelected(nav: MenuItem?): Boolean {
        when (nav?.itemId) {







            R.id.menu_order -> {


                val intent= Intent(this,com.nattapon.appsuckpao.Order::class.java)
                startActivity(intent)

            }

        }

        return super.onOptionsItemSelected(nav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.order_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    }


