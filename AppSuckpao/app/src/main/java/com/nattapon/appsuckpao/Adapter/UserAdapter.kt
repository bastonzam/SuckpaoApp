package com.nattapon.appsuckpao.Adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nattapon.appsuckpao.Data.User

import com.nattapon.appsuckpao.R
import com.squareup.picasso.Picasso


class UserAdapter (val mCtx: Context, val layoutResId:Int, val UsersList:List<User>)
    : ArrayAdapter<User>(mCtx,layoutResId,UsersList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx);
        val view: View =layoutInflater.inflate(layoutResId,null)








        val showmail=view.findViewById<TextView>(R.id.showEmail)
        val showuser=view.findViewById<TextView>(R.id.showUsername)
        val showname=view.findViewById<TextView>(R.id.showName)
        val showlast=view.findViewById<TextView>(R.id.showLastname)
        val showadd=view.findViewById<TextView>(R.id.showAddress)

        val showemail=view.findViewById<TextView>(R.id.mail)
        val showaddress=view.findViewById<TextView>(R.id.address)

        val showImage=view.findViewById<ImageView>(R.id.imageProfile)








        var mail="Email"
        var Add="Address"








        val user = UsersList[position]



        showmail.text=user.email
        showuser.text=user.username
        showname.text=user.name
        showlast.text=user.lastname
        showadd.text=user.address
        Picasso.get().load(user.profileImageUrl).into(showImage)

        showemail.text=mail
        showaddress.text=Add














        return view
    }


}


