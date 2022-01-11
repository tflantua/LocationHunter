package com.bijgepast.locationhunter.models

import android.content.Context
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.bijgepast.locationhunter.database.entities.BaseEntity
import com.bijgepast.locationhunter.utils.DataManager
import java.io.Serializable

open class HintModel(
    private var unLocked: Boolean,
    val hint: String,
    val cost: Int,
    val id: Int
) : BaseObservable(), Serializable, BaseModel {
    private var show: Boolean = false

    @Bindable
    fun getUnlocked(): Boolean {
        return unLocked;
    }

    @Bindable
    fun setUnlocked(context: Context) {
        if (this.unLocked)
            return
        this.unLocked = true
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

        if(this.unLocked)
            DataManager.getInstance().saveUnlocked(this, sharedPreferences.getString("Key", "")!!)

        notifyPropertyChanged(BR.unlocked)
        notifyPropertyChanged(BR.points)
        notifyPropertyChanged(BR.pointSting)
    }

    @Bindable
    fun getShow(): Boolean {
        return this.show
    }

    @Bindable
    fun setShow(show: Boolean) {
        this.show = show
        notifyPropertyChanged(BR.show)
    }

    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }

}