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

        val alertDialog=builder.show()
        view.buttonUpdates.setOnClickListener {
            alertDialog.dismiss()
            val username=view.editTextUsername.text.toString()
            var updateUserName=username
            var map= mutableMapOf<String,Any>()
            map["username"]= updateUserName

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map)


            val name=view.editTextName.text.toString()
            var updateName=name
            var map1= mutableMapOf<String,Any>()
            map1["name"]= updateName

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map1)


            val lastname=view.editTextlastname.text.toString()
            var updateLastName=lastname
            var map2= mutableMapOf<String,Any>()
            map2["lastname"]= updateLastName

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map2)


            val email=view.editTextEmail.text.toString()
            var updateEmail=email
            var map3= mutableMapOf<String,Any>()
            map3["email"]= updateEmail

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map3)

            val address=view.editTextAddress.text.toString()
            var updateAddress=address
            var map4= mutableMapOf<String,Any>()
            map4["address"]= updateAddress

            FirebaseDatabase.getInstance().reference
                .child("Users")
                .child(userId)
                .updateChildren(map4)
        }
        view.buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }










        builder.setPositiveButton("Update") { dialogInterface:DialogInterface, i:Int -> }

        val customDialog=builder.create()
        customDialog.getButton(AlertDialog.BUTTON_POSITIVE)




        builder.setNegativeButton("No") {dialogInterface:DialogInterface, i:Int -> }

    }











    override fun onOptionsItemSelected(nav: MenuItem?): Boolean {
        when (nav?.itemId) {





            R.id.menu_profile -> {


                val intent=Intent(this,Profile::class.java)
                startActivity(intent)

            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, Login::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(nav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



}









