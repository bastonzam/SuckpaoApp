package com.nattapon.appsuckpao.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

import com.nattapon.appsuckpao.Data.OrderOlds
import com.nattapon.appsuckpao.R

class OrderOldAdapter (val mCtx: Context, val layoutResId:Int, val OrderList:List<OrderOlds>)
    : ArrayAdapter<OrderOlds>(mCtx,layoutResId,OrderList) {
    private var mAuth: FirebaseAuth? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx);
        val view: View = layoutInflater.inflate(layoutResId, null)

        val showName = view.findViewById<TextView>(R.id.showName)
        val showNum = view.findViewById<TextView>(R.id.showNum)
        val showLaundry = view.findViewById<TextView>(R.id.showLaundry)
        val showStatus = view.findViewById<TextView>(R.id.showStatus)
        val showDetail = view.findViewById<TextView>(R.id.showDetail)
        val showPrice = view.findViewById<TextView>(R.id.showPrice)

        val showNamec = view.findViewById<TextView>(R.id.storec)
        val showNumc = view.findViewById<TextView>(R.id.numc)
        val showLaundryc = view.findViewById<TextView>(R.id.laundryc)
        val showStatusc = view.findViewById<TextView>(R.id.statusc)
        val showDetailc = view.findViewById<TextView>(R.id.detailc)
        val showPricec = view.findViewById<TextView>(R.id.pricec)



        var numx: String = "จำนวน :"
        val laundryx: String = "รีด/ไม่รีด :"
        val storex: String = "ร้าน :"
        val statusx: String = "สถานะ :"
        val detailx: String = "รายละเอียด :"
        val pricex: String = "ราคา :"


        val order = OrderList[position]

        showNum.text = order.num
        showLaundry.text = order.laundry
        showName.text = order.store
        showStatus.text = order.status
        showDetail.text = order.detail
        showPrice.text = order.price

        showNamec.text = storex
        showNumc.text = numx
        showLaundryc.text = laundryx
        showStatusc.text = statusx
        showDetailc.text = detailx
        showPricec.text = pricex








        return view
    }
}