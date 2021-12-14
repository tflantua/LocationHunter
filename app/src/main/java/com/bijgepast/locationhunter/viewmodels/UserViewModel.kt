package com.bijgepast.locationhunter.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bijgepast.locationhunter.models.UserModel

class UserViewModel : ViewModel() {

    private var userViewModel: MutableLiveData<UserModel> = MutableLiveData()

    fun setUserMode(context: Context) {
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        if (sharedPreferences.contains("Name") &&
            sharedPreferences.contains("Score") &&
            sharedPreferences.contains("Key")
        ) {

            val model = UserModel(
                sharedPreferences.getString("Name", null)!!,
                sharedPreferences.getInt("Score", 0),
                sharedPreferences.getString("Key", null)!!
            )

            this.userViewModel.value = model
        }
    }

    fun getUserModel(): LiveData<UserModel> {
        return this.userViewModel
    }

}