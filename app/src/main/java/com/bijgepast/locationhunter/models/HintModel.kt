package com.bijgepast.locationhunter.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.bijgepast.locationhunter.database.entities.BaseEntity
import com.bijgepast.locationhunter.database.entities.HintsEntity
import com.bijgepast.locationhunter.utils.DataBaseManager
import com.bijgepast.locationhunter.utils.DataManager
import org.w3c.dom.Entity
import java.io.Serializable

class HintModel(
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
    fun setUnlocked(unlocked: Boolean) {
        if (this.unLocked)
            return
        this.unLocked = unlocked

        if(this.unLocked)
            DataManager.getInstance().saveUnlocked(this)

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