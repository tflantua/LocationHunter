package com.bijgepast.locationhunter

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.databinding.ActivityUserBinding
import com.bijgepast.locationhunter.viewmodels.UserViewModel

class UserActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private var _binding: ActivityUserBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        this.userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        if (userViewModel.getUserModel().value == null) {
            this.userViewModel.setUserMode(applicationContext)
        }

        super.onCreate(savedInstanceState)
        this._binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        binding.activity = this
        binding.userModel = userViewModel.getUserModel().value
    }

    fun navigateFriends() {
        startActivity(Intent(this, FriendsActivty::class.java))
    }

    fun logOff() {
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
}