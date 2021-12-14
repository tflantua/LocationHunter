package com.bijgepast.locationhunter.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import java.io.Serializable

class HintModel(
    private var unLocked: Boolean,
    val hint: String,
    val cost: Int
) : BaseObservable(), Serializable {

    @Bindable
    fun getUnlocked(): Boolean {
        return unLocked;
    }

    @Bindable
    fun setUnlocked(unlocked: Boolean) {
        if(this.unLocked)
            return
        this.unLocked = unlocked
        notifyPropertyChanged(BR.unlocked)
        notifyPropertyChanged(BR.points)
    }

}