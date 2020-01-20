package com.example.nattapon.mysuckpao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.nattapon.mysuckpao.Adapter.OrderAdapter
import com.example.nattapon.mysuckpao.DataModel.OrderModel
import com.example.nattapon.mysuckpao.DataModel.OrderRead
import java.util.*
import kotlin.collections.ArrayList

class OrderData : AppCompatActivity() , Observer {


    private var mOrderListAdapter: OrderAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_data)

        OrderModel

        OrderModel.addObserver(this)

        val dataList:ListView=findViewById(R.id.order_list)

        val data: ArrayList<OrderRead> = ArrayList()
        mOrderListAdapter= OrderAdapter(this, R.layout.order_card_item, data)
        dataList.adapter=mOrderListAdapter



    }

    override fun update(o: Observable?, arg: Any?) {
        mOrderListAdapter?.clear()

        val data = OrderModel.getData()
        if(data!=null){
            mOrderListAdapter?.clear()
            mOrderListAdapter?.addAll(data)
            mOrderListAdapter?.notifyDataSetChanged()

        }

    }
    override fun onResume(){
        super.onResume()
        OrderModel.addObserver(this)
    }
    override fun onPause(){
        super.onPause()
        OrderModel.deleteObserver(this)
    }
    override fun onStop(){
        super.onStop()
        OrderModel.deleteObserver(this)
    }
}
