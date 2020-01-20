package com.example.nattapon.mysuckpao

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.user_activity.view.*

class User_Profile(val mCtx:Context,val layoutResId:Int,val userlist:List<User>)
    :ArrayAdapter<User>(mCtx,layoutResId,userlist){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view:View=layoutInflater.inflate(layoutResId,null)

        val textViewName=view.findViewById<TextView>(R.id.textViewName)

        val user=userlist[position]

        textViewName.text=user.username

        return view;
    }
}