package com.example.nattapon.mysuckpao

import com.google.firebase.database.Exclude
class User(val uid: String, val username: String, val profileImageUrl: String,val  email:String){
    constructor() : this("","","","")
}