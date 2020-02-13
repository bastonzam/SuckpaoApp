package com.nattapon.appsuckpao.Data


class OrderOlds(val orderid:String,
            val num:String,
            val laundry:String,
            val store:String,
            val status:String,
            val place:String,
            val detail:String,
            val price:String){
    constructor():this("","","","","","","","")
}