package com.nattapon.appsuckpao.Data

class Order(val orderid:String,
            val num:String,
            val laundry:String,
            val store:String,
            val status:String,
            val place:String){
    constructor():this("","","","","","")
}
/*
{
    var orderid:String?=null
    var num:String?=null
    var laundry:String?=null
    var store:String?=null
    var status:String?=null

    /*constructor(orderid:String,num:String,laundry:String,store:String,status:String){
        this.orderid=orderid
        this.num=num
        this.laundry=laundry
        this.store=store
        this.status=status

    }*/

}*/
