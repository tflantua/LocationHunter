package com.bijgepast.locationhunter.utils

import android.content.Context
import android.content.Intent
import com.bijgepast.locationhunter.LoginActivity

fun Context.logOut() {
    val sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    sharedPreferences.edit()
        .remove("Name")
        .remove("Key")
        .remove("Score")
        .apply()

    val i = Intent(this, LoginActivity::class.java)
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(i)
}