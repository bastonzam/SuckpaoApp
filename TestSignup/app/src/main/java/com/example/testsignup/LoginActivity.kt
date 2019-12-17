package com.example.testsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_sign_up
import kotlinx.android.synthetic.main.activity_main.tv_password
import kotlinx.android.synthetic.main.activity_main.tv_username
import kotlinx.android.synthetic.main.activity_sign_up.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btn_sign_up.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
        btn_log_in.setOnClickListener{
            doLogin()
        }
    }

    private fun doLogin() {
        if(tv_username.text.toString().isEmpty()){
            tv_username.error ="กรุรากรอกอีเมล์"
            tv_username.requestFocus()
            return

        }
        if(!Patterns.EMAIL_ADDRESS.matcher(tv_username.text.toString()).matches()){
            tv_username.error ="กรุณาตรวจสอบรูปแบบอีเมล์"
            tv_username.requestFocus()
            return
        }
        if(tv_password.text.toString().isEmpty()){
            tv_password.error="กรุณากรอกรหัสผ่าน"
            tv_password.requestFocus()
            return
        }
        auth.signInWithEmailAndPassword(tv_username.text.toString(), tv_password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(currentUser :FirebaseUser?){

       if(currentUser!=null) {
           if (currentUser.isEmailVerified) {
               startActivity(Intent(this, HomeActivity::class.java))
           } else {
               Toast.makeText(
                   baseContext, "Please verify your email address.",
                   Toast.LENGTH_SHORT
               ).show()
           }
       }

        else {
           Toast.makeText(
               baseContext, "Login failed.",
               Toast.LENGTH_SHORT
           ).show()
       }
    }

}
