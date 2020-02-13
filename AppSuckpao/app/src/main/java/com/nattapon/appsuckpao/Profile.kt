package com.nattapon.appsuckpao

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

import com.nattapon.appsuckpao.Adapter.UserAdapter
import com.nattapon.appsuckpao.Data.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.update_user.*
import kotlinx.android.synthetic.main.update_user.view.*

class Profile : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    private var mAuth: FirebaseAuth? = null
    lateinit var user: MutableList<User>
    lateinit var ref: DatabaseReference
    lateinit var listView: ListView
    private val currentUser = FirebaseAuth.getInstance().currentUser

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    private var tvFirstName: TextView? = null
    private var tvLastName: TextView? = null
    private var tvEmail: TextView? = null
    private var tvUsername: TextView? = null
    private var tvAddress: TextView? = null
    private var imageView: CircleImageView? = null







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        initialise()
        updateProfile.setOnClickListener {
            showUpdate()
            Log.d("Profile","Click on update")
        }

    }
    private fun initialise() {
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        tvFirstName = findViewById<View>(R.id.tv_first_name) as TextView
        tvLastName = findViewById<View>(R.id.tv_last_name) as TextView
        tvEmail = findViewById<View>(R.id.tv_email) as TextView
        tvUsername = findViewById<View>(R.id.tv_first_username) as TextView
        tvAddress = findViewById<View>(R.id.tv_address) as TextView

        imageView=findViewById<View>(R.id.imageProfile2) as CircleImageView






    }
    override fun onStart() {
        super.onStart()
        var link:String?=null
        val mUser = mAuth!!.currentUser
        val mUserReference = mDatabaseReference!!.child(mUser!!.uid)
        tvEmail!!.text = mUser.email
        mUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tvFirstName!!.text = snapshot.child("name").value as String
                tvLastName!!.text = snapshot.child("lastname").value as String
                tvUsername!!.text = snapshot.child("username").value as String
                tvAddress!!.text = snapshot.child("address").value as String
                 link= snapshot.child("profileImageUrl").value as String

                Picasso.get().load(link).into(imageView)




            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    private fun showUpdate(){
        val userId  =  mAuth!!.currentUser!!.uid



        val view = LayoutInflater.from(this).inflate(R.layout.update_user,null)
        val builder=AlertDialog.Builder(this).setTitle("แก้ไขโปรไฟล์").setView(view)

//        val alertDialog=builder.show()
//        view.buttonUpdates.setOnClickListener {
//            alertDialog.dismiss()

            val editText=view.findViewById<EditText>(R.id.editTextUsername)
            editText.setText(tvUsername?.text.toString())

        val editText2=view.findViewById<EditText>(R.id.editTextName)
        editText2.setText(tvFirstName?.text.toString())

        val editText3=view.findViewById<EditText>(R.id.editTextlastname)
        editText3.setText(tvLastName?.text.toString())

        val editText4=view.findViewById<EditText>(R.id.editTextEmail)
        editText4.setText(tvEmail?.text.toString())

        val editText5=view.findViewById<EditText>(R.id.editTextAddress)
        editText5.setText(tvAddress?.text.toString())

        builder.setView(view)
        builder.setPositiveButton("Update"){p0,p1->


            val username=editText.text.toString()
            var updateUserName=username
            var map= mutableMapOf<String,Any>()
            map["username"]= updateUserName

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map)


            val name=editText2.text.toString()
            var updateName=name
            var map1= mutableMapOf<String,Any>()
            map1["name"]= updateName

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map1)


            val lastname=editText3.text.toString()
            var updateLastName=lastname
            var map2= mutableMapOf<String,Any>()
            map2["lastname"]= updateLastName

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map2)


            val email=editText4.text.toString()
            var updateEmail=email
            var map3= mutableMapOf<String,Any>()
            map3["email"]= updateEmail

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map3)

            val address=editText5.text.toString()
            var updateAddress=address
            var map4= mutableMapOf<String,Any>()
            map4["address"]= updateAddress

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map4)
        }

        builder.setNegativeButton("No"){p0,p1->}
        val alert=builder.create()
        alert.show()



    }











    override fun onOptionsItemSelected(nav: MenuItem?): Boolean {
        when (nav?.itemId) {





            R.id.menu_profile -> {


                val intent=Intent(this,Profile::class.java)
                startActivity(intent)

            }
            R.id.menu_sign_out -> {
                val builder= AlertDialog.Builder(this)
                builder.setTitle("ออกจากระบบ")
                builder.setMessage("แน่ใจหรือไม่ว่าต้องการออกจากระบบ")

                builder.setPositiveButton("ใช่",{ dialogInterface: DialogInterface, i: Int ->
                    logout()
                })

                builder.setNegativeButton("ไม่",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()
            }
        }

        return super.onOptionsItemSelected(nav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }



}









