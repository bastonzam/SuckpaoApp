package com.nattapon.appsuckpao.Data


class User(val uid:String,
           val username:String,
               val profileImageUrl:String,
               val email:String,
               val name:String,
               val lastname:String,
               val address:String) {
    constructor() : this("","", "", "", "", "", "")
    constructor( uid:String,username: String, name: String, lastname: String, email: String, address: String) : this()
}
