package com.bijgepast.locationhunter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bijgepast.locationhunter.databinding.ActivityFriendsBinding
import com.bijgepast.locationhunter.models.FriendModel
import com.bijgepast.locationhunter.viewmodels.FriendsViewModel

class FriendsActivty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //super needs to be called before on set content view else the theme wont render properly
        super.onCreate(savedInstanceState)

        val friendsViewModel = FriendsViewModel()
        if (friendsViewModel.getFriends().isEmpty())
            friendsViewModel.setFriends(
                listOf(
                    FriendModel("Thomas", 200, 3),
                    FriendModel("Thomas2", 400, 6)
                )
            )

        val binding = ActivityFriendsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.Name.text = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).getString("Name", "")!!

        binding.friendsModel = friendsViewModel

        binding.BackButton.setOnClickListener{
            finish()
        }

        binding.Requests.setOnClickListener{

        }

        binding.Search.setOnClickListener{

        }

    }
}