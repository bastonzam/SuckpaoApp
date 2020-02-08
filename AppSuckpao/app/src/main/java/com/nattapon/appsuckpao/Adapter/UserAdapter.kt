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
        val textViewUpdate=view.findViewById<TextView>(R.id.textViewUpdate)







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

        /*textViewUpdate.setOnClickListener {
            showUpdateDialog(user)
        }*/

       // showImage.setOnClickListener{
          //  var userid=user.userid
          //  Log.d("Profile","User ID is $userid")

       // }













        return view
    }/*
    fun showUpdateDialog(user: User) {


        val uid = FirebaseAuth.getInstance().currentUser!!.uid

        val builder= AlertDialog.Builder(mCtx)
        builder.setTitle("Update User")

        val inflater =LayoutInflater.from(mCtx)

        val view=inflater.inflate(R.layout.update_user,null)

        val editUsernames=view.findViewById<EditText>(R.id.editTextUsername)
        val editNames=view.findViewById<EditText>(R.id.editTextName)
        val editLastnames=view.findViewById<EditText>(R.id.editTextlastname)
        val editEmails=view.findViewById<EditText>(R.id.editTextEmail)
        val editAddresss=view.findViewById<EditText>(R.id.editTextAddress)

        editUsernames.setText(user.username)
        editNames.setText(user.name)
        editLastnames.setText(user.lastname)
        editEmails.setText(user.email)
        editAddresss.setText(user.address)


        builder.setView(view)

        builder.setPositiveButton("Update") { p0, p1 -> }

        val dbUser=FirebaseDatabase.getInstance().getReference("Users")

        val username=editUsernames.text.toString().trim()
        val name=editNames.text.toString().trim()
        val lastname=editLastnames.text.toString().trim()
        val email=editEmails.text.toString().trim()
        val address=editAddresss.text.toString().trim()
       /* if(username.isEmpty()||name.isEmpty()||lastname.isEmpty()||email.isEmpty()||address.isEmpty()){
            editUsernames.error="กรุณากรอกทุกช่อง"
            editUsernames.requestFocus()
            return
        }*/
        val user = User(user.uid,username,name,lastname,email,address)

        dbUser.child(uid).setValue(user)




        builder.setNegativeButton("No") { p0, p1 -> }
        val alert=builder.create()
        alert.show()


    }*/


}


