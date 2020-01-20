package com.example.nattapon.mysuckpao

import android.content.Context
import android.widget.Toast

import com.google.firebase.database.DataSnapshot

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this,text,duration).show()
}
/*
fun DataSnapshot.asUser(): User? =
    getValue(User::class.java)?.copy(uid=key)

private fun Any?.copy(uid: Any?): User? {
    return null

}
*/