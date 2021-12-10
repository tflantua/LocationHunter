package com.bijgepast.locationhunter.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

class HintModel(
    private var unLocked: Boolean,
    val hint: String,
    val cost: Int
) : BaseObservable() {

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