package com.bijgepast.locationhunter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.FragmentHost, LoginFragment())
        ft.commit()
    }
}