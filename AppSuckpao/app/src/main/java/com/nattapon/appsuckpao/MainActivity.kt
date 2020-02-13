package com.nattapon.appsuckpao

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nattapon.appsuckpao.Adapter.OrderAdapter
import com.nattapon.appsuckpao.Data.Order
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.orders.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference
    private var mAuth: FirebaseAuth? = null





    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    //progressbar
    private var mProgressBar: ProgressDialog? = null


    val REQUEST_CODE=1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //progressbar
        mProgressBar = ProgressDialog(this)

        //checkpermission //takelocation

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
        else
        {
            buildLocationRequest()
            buildLocationCallBack()

            fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

            //set event

            btn_start_updates.setOnClickListener (View.OnClickListener {

                if(ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.ACCESS_FINE_LOCATION )!=PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED )
                {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
                    return@OnClickListener
                }
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,
                    Looper.myLooper())

                //change state
                btn_start_updates.isEnabled = !btn_start_updates.isEnabled
                btn_stop_updates.isEnabled=!btn_stop_updates.isEnabled
            })
            btn_stop_updates.setOnClickListener(View.OnClickListener {
                if(ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.ACCESS_FINE_LOCATION )!=PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.ACCESS_COARSE_LOCATION )!=PackageManager.PERMISSION_GRANTED )
                {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),REQUEST_CODE)
                    return@OnClickListener
                }
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)

                //change state
                btn_start_updates.isEnabled = !btn_start_updates.isEnabled
                btn_stop_updates.isEnabled=!btn_stop_updates.isEnabled
            })
        }








        //////saveorder
        saveOrderBtn.setOnClickListener {
            saveorder()




        }






    }

    private fun buildLocationCallBack() {
        locationCallback=object :LocationCallback(){

            override fun onLocationResult(p0: LocationResult?) {
                var location = p0!!.locations.get(p0.locations.size-1)
                txt_location.text=location.latitude.toString()+"/"+location.longitude.toString()
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval=5000
        locationRequest.fastestInterval=3000
        locationRequest.smallestDisplacement=10f
    }

    fun saveorder(){






        val uid = FirebaseAuth.getInstance().uid ?: ""

        val num =spinner2.selectedItem.toString()
        val laundry =spinner3.selectedItem.toString()
        val store =spinner4.selectedItem.toString()
        val place =txt_location.text.toString()
        val detail =etDetail.text.toString()


        val ref = FirebaseDatabase.getInstance().getReference("order/$uid")

        val orderid=ref.push().key
        val order = Order(orderid!!, num, laundry, store, "wait",place,detail,"-")

        ref.child(orderid).setValue(order)
            .addOnSuccessListener {

                mProgressBar!!.setMessage("กำลังส่งออเดอร์")
                mProgressBar!!.show()
                Log.d("Main", "Finally we saved the order to Firebase Database")
                Toast.makeText(this,"ส่งออเดอร์สำเร็จ!", Toast.LENGTH_LONG).show()
                mProgressBar!!.hide()
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to set value to database: ${it.message}")
            }


    }

    fun saveorderold(){






        val uid = FirebaseAuth.getInstance().uid ?: ""

        val num =spinner2.selectedItem.toString()
        val laundry =spinner3.selectedItem.toString()
        val store =spinner4.selectedItem.toString()
        val place =txt_location.text.toString()
        val detail =etDetail.text.toString()

        val ref = FirebaseDatabase.getInstance().getReference("orderold/$uid")

        val orderid=ref.push().key
        val order = Order(orderid!!, num, laundry, store, "wait",place,detail,"-")

        ref.child(orderid).setValue(order)
            .addOnSuccessListener {
                Log.d("Main", "Finally we saved the order to Firebase Database")
                Toast.makeText(this,"Order pass", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to set value to database: ${it.message}")
            }


    }


    ////menu


    override fun onOptionsItemSelected(nav: MenuItem?): Boolean {
        when (nav?.itemId) {





            R.id.menu_profile -> {


                val intent=Intent(this,Profile::class.java)
                startActivity(intent)

            }
            R.id.menu_order -> {


                val intent=Intent(this,com.nattapon.appsuckpao.Order::class.java)
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

