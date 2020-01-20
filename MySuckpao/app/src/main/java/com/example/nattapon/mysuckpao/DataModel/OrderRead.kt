package com.example.nattapon.mysuckpao.DataModel

import com.google.firebase.database.DataSnapshot
import java.lang.Exception

class OrderRead(snapshot: DataSnapshot){
    lateinit var  uid: String
    lateinit var  Type: String
    lateinit var Num: String
    lateinit var Laundry: String
    lateinit var Store: String

    init {
        try{
            @Suppress("UNCHECKED_CAST") val data:HashMap<String,Any> = snapshot.value as HashMap<String, Any>
            uid = snapshot.key ?:""
            Type = data["type"] as String
            Num = data["num"] as String
            Laundry = data["laundry"] as String
            Store = data["store"] as String
        }catch (e: Exception) {
            e.printStackTrace()
        }}




}