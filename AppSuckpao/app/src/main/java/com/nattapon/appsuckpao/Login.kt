package com.nattapon.appsuckpao

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*



class Login : AppCompatActivity() {

    private var mIsShowpass=false

    private var mProgressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mProgressBar = ProgressDialog(this)



        ivShowHidePassword.setOnClickListener{
            mIsShowpass= !mIsShowpass
            ShowPassword(mIsShowpass)
        }
        ShowPassword(mIsShowpass)

        btn_log_in.setOnClickListener {
            doLogin()

        }
        forgotpassword.setOnClickListener {

           doForgot()
        }
        donhaveaccount.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }



    }

    private fun doLogin() {
        val email = useremail_login.text.toString()
        val password = password_login.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(findViewById(R.id.login),"กรุณากรอกอีเมล์ หรือรหัสผ่าน",Snackbar.LENGTH_LONG).show()
            return
        }


        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener



                mProgressBar!!.setMessage("กำลังล็อกอิน")
                mProgressBar!!.show()

                Snackbar.make(findViewById(R.id.login),"ล็อกกินสำเร็จ",Snackbar.LENGTH_LONG).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to log in: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
    fun bucketname(){
        val opts = FirebaseApp.getInstance()!!.options
        Log.i("Login" ,"Bucket = " + opts.storageBucket)
    }

    private fun doForgot(){
        val i=Intent(this,ForgotPasswordActivity::class.java)
        startActivity(i)


    }
    private fun ShowPassword(isShow:Boolean){
        if(isShow){
            password_login.transformationMethod= HideReturnsTransformationMethod.getInstance()
            ivShowHidePassword.setImageResource(R.drawable.ic_hides)

        }
        else{
            password_login.transformationMethod= PasswordTransformationMethod.getInstance()
            ivShowHidePassword.setImageResource(R.drawable.ic_show)
        }
        password_login.setSelection(password_login.text.toString().length)
    }


}



