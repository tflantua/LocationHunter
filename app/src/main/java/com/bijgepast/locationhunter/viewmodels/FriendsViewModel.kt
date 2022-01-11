package com.bijgepast.locationhunter.viewmodels

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.models.FriendModel
import com.bijgepast.locationhunter.models.RiddleModel

class FriendsViewModel : BaseObservable() {
    private var friendModels: MutableList<FriendModel> = ArrayList()

    @Bindable
    fun setFriends(friendModels: List<FriendModel>) {
        this.friendModels.clear()
        this.friendModels.addAll(friendModels)
        notifyPropertyChanged(BR.friends)
    }

    @Bindable
    fun getFriends(): List<FriendModel> {
        return friendModels
    }

}