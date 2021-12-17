package com.bijgepast.locationhunter.models

import android.location.Location
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.utils.GpsManager
import com.bijgepast.locationhunter.utils.getStatusFromDistance

import java.io.Serializable


class RiddleModel(
    val locationModel: LocationModel,
    val RiddleName: String,
    val riddle: String,
    val difficulty: Int,
    val hints: List<HintModel>,
    private val points: Int,
    private var distanceStatus: DistanceStatus,
    private var completed: Boolean
) : BaseObservable(), Serializable, GpsManager.GpsCallback {

    @Bindable
    fun getCompleted(): Boolean {
        return this.completed
    }

    @Bindable
    fun setCompleted(boolean: Boolean) {
        if (!this.completed) {
            this.completed = boolean
            notifyPropertyChanged(BR.completed)
        }
    }

    @Bindable
    fun getPoints(): Int {
        var points = this.points;
        this.hints.forEach { hintModel ->
            if (hintModel.getUnlocked())
                points -= hintModel.cost
        }

        return 10.coerceAtLeast(points)
    }

    @Bindable
    fun getPointSting(): String {
        return "reward: $points"
    }

    @Bindable
    fun getStatus(): DistanceStatus {
        return this.distanceStatus
    }

    @Bindable
    fun getStatusString(): String {
        return "Status: ${this.distanceStatus.nameForEnum}"
    }

    @Bindable
    fun setStatus(status: DistanceStatus) {
        this.distanceStatus = status
        notifyPropertyChanged(BR.status)
        notifyPropertyChanged(BR.statusString)
    }



    override fun updateLocation(long: Double, lat: Double) {
        val floatArray: FloatArray = FloatArray(3)
        Location.distanceBetween(lat, long, locationModel.north, locationModel.east, floatArray)
        this.setStatus(getStatusFromDistance(floatArray[0]))
    }


}