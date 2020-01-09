package com.example.nattapon.mysuckpao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*

class HomeActivity : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        verifyUserIsLoggedIn()


        buttonSave.setOnClickListener {
            saveOrder()
        }

    }

    private fun saveOrder(){
        val num =spinner2.textAlignment.toString()
        val type =spinner1.textAlignment.toString()
        val laundry =spinner3.textAlignment.toString()
        val store =spinner4.textAlignment.toString()
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/order/$uid")

        val order = Order(uid, type,num,laundry,store)

        ref.setValue(order)
            .addOnSuccessListener {
                Log.d("Homeactivity", "Finally we saved the user to Firebase Database")
                Toast.makeText(this,"Order pass",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Log.d("Homeactivity", "Failed to set value to database: ${it.message}")
            }

    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_new_order -> {

            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }




}

class Order(val uid: String, val Type: String, val Num: String, val Laundry: String, val Store: String)
