package com.example.nattapon.mysuckpao.DataModel

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

object OrderModel: Observable(){

    private var mValueDataListener: ValueEventListener?=null
    private var mOrderList: ArrayList<OrderRead>?= ArrayList()

    private  fun getDatabaseRef(): DatabaseReference?{
        val uid = FirebaseAuth.getInstance().uid ?: ""
        return FirebaseDatabase.getInstance().reference.child("order")

    }

    init {
        if(mValueDataListener !=null){
            getDatabaseRef()
                ?.removeEventListener(mValueDataListener!!)
        }
        mValueDataListener =null
        Log.i("OrderModel","data init kine 26")

        mValueDataListener =object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    Log.i("CakeModel","data update line 29")
                    val data:ArrayList<OrderRead> = ArrayList()
                    if(dataSnapshot!=null){
                        for(snapshot: DataSnapshot in dataSnapshot.children){
                            try {
                                data.add(
                                    OrderRead(
                                        snapshot
                                    )
                                )

                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                        mOrderList =data
                        Log.i("OrderModel","data update, there are"+ mOrderList!!.size+" entrees in the cache")
                        setChanged()
                        notifyObservers()
                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }

            }
            override fun onCancelled(p0: DatabaseError) {
                if(p0!= null){
                    Log.i("OrderModel","line 51 data updae canceled = ${p0.message}")
                }

            }



        }
        getDatabaseRef()
            ?.addValueEventListener(mValueDataListener as ValueEventListener)
    }

    fun getData():ArrayList<OrderRead>?{
        return mOrderList
    }
}