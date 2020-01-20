package com.example.nattapon.mysuckpao.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.nattapon.mysuckpao.DataModel.OrderRead
import com.example.nattapon.mysuckpao.R
import java.lang.Exception

class OrderAdapter(context: Context,resource:Int,list:ArrayList<OrderRead>)
    :ArrayAdapter<OrderRead>(context,resource,list) {

    private var mResource:Int=0
    private lateinit var mList:ArrayList<OrderRead>
    private lateinit var mLayoutInflator:LayoutInflater
    private var mContext=context

    lateinit var typec:TextView
    lateinit var numc:TextView
    lateinit var laundryc:TextView
    lateinit var storec:TextView

    init {
        this.mResource=resource
        this.mList=list
        this.mLayoutInflator=mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }


    override fun getView(position: Int,convertView: View?,parent: ViewGroup): View {
        val returnView:View?
        if(convertView==null){
            returnView=try {
                mLayoutInflator.inflate(mResource,null)

            }catch (e: Exception){
                e.printStackTrace()
                View(context)
            }
            setUI(returnView,position)
            return returnView
        }
        setUI(convertView,position)
        return convertView
    }
    private fun setUI(view: View,position: Int){
        val order: OrderRead?=if(count>position) getItem(position) else null
        val type:TextView?=view.findViewById(R.id.cake_card_type)
        type?.text=order?.Type?:""

        val num:TextView?=view.findViewById(R.id.cake_card_num)
        num?.text=order?.Num?:""

        val laundry:TextView?=view.findViewById(R.id.cake_card_laundry)
        laundry?.text=order?.Laundry?:""

        val store:TextView?=view.findViewById(R.id.cake_card_store)
        store?.text=order?.Store?:""




    }

}


