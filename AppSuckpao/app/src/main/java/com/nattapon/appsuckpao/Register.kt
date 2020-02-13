package com.nattapon.appsuckpao

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.nattapon.appsuckpao.Adapter.UserAdapter
import com.nattapon.appsuckpao.Data.Order
import com.nattapon.appsuckpao.Data.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class Register : AppCompatActivity() {

    companion object {
        val TAG = "RegisterActivity"
    }






    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null



    lateinit var ref: DatabaseReference

    private var mAuth: FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    lateinit var user:MutableList<User>

    lateinit var listView: ListView

    private var mIsShowpass=false

    private var mProgressBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        mProgressBar = ProgressDialog(this)


        ivShowHidePassword.setOnClickListener{
            mIsShowpass= !mIsShowpass
            ShowPassword(mIsShowpass)
        }
        ShowPassword(mIsShowpass)

        /*val uid = FirebaseAuth.getInstance().uid ?: ""
        ref = FirebaseDatabase.getInstance().getReference("Users/$uid")*/
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()



        sign_upBtn.setOnClickListener {
            performRegister()
        }

        sign_upBtn_login.setOnClickListener {
            Log.d(TAG, "Try to show login activity")

            // launch the login activity somehow
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        selectphoto_button_register.setOnClickListener {
            Log.d(TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)








        }
    }



    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)

            selectphoto_button_register.alpha = 0f


        }
    }

    private fun performRegister() {
        val email = useremail_register.text.toString()
        val password = password_register.text.toString()
        mAuth!!.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->

                if (task.isSuccessful){
                    Toast.makeText(applicationContext,"Successful register",Toast.LENGTH_LONG).show()


                    uploadImageToFirebaseStorage()


                }else
                {
                    Toast.makeText(applicationContext,"fail register",Toast.LENGTH_LONG).show()
                }

            }

    }

    private fun uploadImageToFirebaseStorage() {



        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                //Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location: $it")
                    mProgressBar!!.setMessage("Registering User...")
                    mProgressBar!!.show()

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {




        val userId  =  mAuth!!.currentUser!!.uid
        val username=username_register?.text.toString()
        val Email=useremail_register?.text.toString()
        val firstName=name_register?.text.toString()
        val lastName=lastname_register?.text.toString()
        val address=address_register?.text.toString()





        val currentUserDb = mDatabaseReference!!.child(userId)

        currentUserDb.child("uid").setValue(userId)
        currentUserDb.child("username").setValue(username)
        currentUserDb.child("profileImageUrl").setValue(profileImageUrl)
        currentUserDb.child("email").setValue(Email)
        currentUserDb.child("name").setValue(firstName)
        currentUserDb.child("lastname").setValue(lastName)
        currentUserDb.child("address").setValue(address)


            .addOnSuccessListener {
                Log.d("Main", "Finally we saved the order to Firebase Database")
                Toast.makeText(this,"Success!", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to set value to database: ${it.message}")
            }
        LoadTweets()


    }
    override fun onStart() {
        super.onStart()
        LoadTweets()
    }

    fun LoadTweets(){
        var currentUser =mAuth!!.currentUser

        if(currentUser!=null) {


            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)
        }
    }
    private fun ShowPassword(isShow:Boolean){
        if(isShow){
            password_register.transformationMethod=HideReturnsTransformationMethod.getInstance()
            ivShowHidePassword.setImageResource(R.drawable.ic_hides)

        }
        else{
            password_register.transformationMethod=PasswordTransformationMethod.getInstance()
            ivShowHidePassword.setImageResource(R.drawable.ic_show)
        }
        password_register.setSelection(password_register.text.toString().length)
    }

}

