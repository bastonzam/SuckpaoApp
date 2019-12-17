package com.example.testsignup

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    var fbAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val typeface = Typeface.createFromAsset(assets, "FC Active Light.ttf")

        var btnLogOut = findViewById<Button>(R.id.btnLogout)

        btnLogOut.text = "ออกจากระบบจ้า"   // Welcome in Tamil
        // Set the typeface
        btnLogOut.typeface = typeface
        btnLogOut.setOnClickListener{ view ->
            showMessage(view, "Logging Out...")
            signOut()
        }

        fbAuth.addAuthStateListener {
            if(fbAuth.currentUser == null){
                this.finish()
            }
        }
    }

    fun signOut(){
        fbAuth.signOut()

    }

    fun showMessage(view: View, message: String){
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show()






}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main,menu)
        return true
    }



}
