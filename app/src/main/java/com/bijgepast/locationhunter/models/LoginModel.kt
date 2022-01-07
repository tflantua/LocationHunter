package com.bijgepast.locationhunter.models

import android.app.Activity
import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.library.baseAdapters.BR
import com.bijgepast.locationhunter.R
import com.bijgepast.locationhunter.SignupFragment
import com.bijgepast.locationhunter.database.entities.BaseEntity
import com.bijgepast.locationhunter.utils.ApiHandler

class LoginModel : BaseObservable(), BaseModel {
    private var isLoading: Boolean = false

    fun getIsLoading(): Boolean {
        return this.isLoading
    }

    fun setIsLoading(loading: Boolean) {
        this.isLoading = loading
        notifyPropertyChanged(BR.loginData)
    }

    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }
}