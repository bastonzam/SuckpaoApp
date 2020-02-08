package com.nattapon.appsuckpao.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.nattapon.appsuckpao.Data.Order
import com.nattapon.appsuckpao.R
import kotlinx.android.synthetic.main.orders.view.*

class OrderAdapter (val mCtx:Context,val layoutResId:Int,val OrderList:List<Order>)
    :ArrayAdapter<Order>(mCtx,layoutResId,OrderList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx);
        val view:View=layoutInflater.inflate(layoutResId,null)

        val showName=view.findViewById<TextView>(R.id.showName)
        val showNum=view.findViewById<TextView>(R.id.showNum)
        val showLaundry=view.findViewById<TextView>(R.id.showLaundry)
        val showStatus=view.findViewById<TextView>(R.id.showStatus)

        val showNamec=view.findViewById<TextView>(R.id.storec)
        val showNumc=view.findViewById<TextView>(R.id.numc)
        val showLaundryc=view.findViewById<TextView>(R.id.laundryc)
        val showStatusc=view.findViewById<TextView>(R.id.statusc)

        var numx:String="จำนวน :"
        val laundryx:String="รีด/ไม่รีด :"
        val storex:String="ร้าน :"
        val statusx:String="สถานะ :"



        val order = OrderList[position]

        showNum.text=order.num
        showLaundry.text=order.laundry
        showName.text=order.store
        showStatus.text=order.status

        showNamec.text=storex
        showNumc.text=numx
        showLaundryc.text=laundryx
        showStatusc.text=statusx






        return view
    }


}