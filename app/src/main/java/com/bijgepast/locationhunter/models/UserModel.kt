package com.bijgepast.locationhunter.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.database.entities.BaseEntity

class UserModel(val userName: String, private var score: Int, private val key: String) :
    BaseObservable(), BaseModel {

    private val locations: MutableList<LocationModel> = ArrayList()

    @Bindable
    fun getLocations(): MutableList<LocationModel> {
        return this.locations
    }

    @Bindable
    fun setLocation(locationModel: LocationModel) {
        this.locations.add(locationModel)
        notifyPropertyChanged(BR.locations)
    }

    @Bindable
    fun setLocations(locations: List<LocationModel>) {
        this.locations.clear()
        this.locations.addAll(locations)
        notifyPropertyChanged(BR.locations)
    }


    @Bindable
    fun setScore(addScore: Int) {
        this.score += addScore
        notifyPropertyChanged(BR.score)
    }

    @Bindable
    fun getScore(): Int {
        return this.score
    }

    fun getKey(): String {
        return this.key
    }

    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }


}