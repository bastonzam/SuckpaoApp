package com.example.nattapon.mysuckpao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class HomeActivity : AppCompatActivity() {











    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        verifyUserIsLoggedIn()

/*
        val type = arrayOf("เสื้อ","กางเกง")
        val mySpinner = findViewById(R.id.spinner1)as Spinner

        var adapter = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,type)

        mySpinner.adapter=adapter

        mySpinner.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View, i: Int, id: Long
            )
            {
                Toast.makeText(this@HomeActivity,type[i],Toast.LENGTH_SHORT).show()

            } override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }
        val num = arrayOf("1","2","3","4","5","6","7","8","9","10")
        val mySpinner2 = findViewById(R.id.spinner2)as Spinner

        var adapter2 = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,type)

        mySpinner2.adapter=adapter2

        mySpinner2.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, i: Int, id: Long
            ) {
                Toast.makeText(this@HomeActivity, num[i], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        val laundry = arrayOf("รีด","ไม่รีด")
        val mySpinner3 = findViewById(R.id.spinner3)as Spinner

        var adapter3 = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,type)

        mySpinner3.adapter=adapter3

        mySpinner3.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, i: Int, id: Long
            ) {
                Toast.makeText(this@HomeActivity, laundry[i], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        val store = arrayOf("ป้าใต้หอ ","ลุงข้างบ้าน")
        val mySpinner4 = findViewById(R.id.spinner4)as Spinner

        var adapter4 = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,type)

        mySpinner4.adapter=adapter4

        mySpinner4.onItemSelectedListener=object :AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, i: Int, id: Long
            ) {
                Toast.makeText(this@HomeActivity, store[i], Toast.LENGTH_SHORT).show()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
*/


        buttonSave.setOnClickListener {


            saveOrder()
        }





    }

    private fun saveOrder(){
        val num =spinner2.selectedItem.toString()
        val type =spinner1.selectedItem.toString()
        val laundry =spinner3.selectedItem.toString()
        val store =spinner4.selectedItem.toString()
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("order/$uid")

        val order = Order(uid,type,num,laundry,store)

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
                val intent=Intent(this,OrderData::class.java)
                startActivity(intent)
                showToast("Enter pass")


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
class Order(   val uid: String,
               val Type: String,
               val Num: String,
               val Laundry: String,
               val Store: String)


