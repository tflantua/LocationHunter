package com.bijgepast.locationhunter.models

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.location.Location
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.database.entities.BaseEntity
import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.utils.DataManager
import com.bijgepast.locationhunter.utils.GpsCallback
import com.bijgepast.locationhunter.utils.bearingToDegrees
import com.bijgepast.locationhunter.utils.getStatusFromDistance
import java.io.Serializable
import kotlin.math.roundToInt


open class RiddleModel(
    val locationModel: LocationModel,
    val riddleName: String,
    val riddle: String,
    val difficulty: Int,
    val hintsList: List<HintModel> = ArrayList(),
    private val points: Int,
    @Transient
    private var distanceStatus: DistanceStatus = DistanceStatus.FROZEN,
    private var visited: Boolean,
    var ID: Int
) : BaseObservable(), Serializable, GpsCallback, SensorEventListener, BaseModel {

    @Bindable
    fun getCompleted(): Boolean {
        return this.visited
    }

    @Bindable
    fun setCompleted(context: Context) {
        if (!this.visited) {
            this.visited = true
            val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)

            DataManager.getInstance().saveVisited(this, sharedPreferences.getString("Key", "")!!)

            notifyPropertyChanged(BR.completed)
        }
    }

    @Bindable
    fun getPoints(): Int {
        var points = this.points
        this.hintsList.forEach { hintModel ->
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
        return if(this.distanceStatus != null)
            "Status: ${this.distanceStatus.nameForEnum}"
        else{
            this.distanceStatus = DistanceStatus.FROZEN
            "Status: ${this.distanceStatus.nameForEnum}"
        }
    }

    @Bindable
    fun setStatus(status: DistanceStatus) {
        this.distanceStatus = status
        notifyPropertyChanged(BR.status)
        notifyPropertyChanged(BR.statusString)
    }

    override fun updateLocation(location: Location, context: Context) {
        val floatArray = FloatArray(2)

        Location.distanceBetween(
            location.latitude,
            location.longitude,
            locationModel.north,
            locationModel.east,
            floatArray
        )

//        val gmf = GeomagneticField(
//            location.latitude.toFloat(),
//            location.longitude.toFloat(),
//            location.altitude.toFloat(),
//            System.currentTimeMillis()
//        )

//        this.declination = gmf.declination

        this.setStatus(getStatusFromDistance(floatArray[0]))
        if (this.distanceStatus == DistanceStatus.REACHED)
            setCompleted(context)

        newTargetDegree = -bearingToDegrees(floatArray[1])

        currentTargetDegree = newTargetDegree

    }

    private var currentTargetDegree: Float = 0f
    private var currentCompasDegree: Float = 0f

    private var newTargetDegree: Float = 0f


//    private val geomagnetic: FloatArray = FloatArray(3)
//    private val gravity: FloatArray = FloatArray(3)
//    private var haveacc = false
//    private var havemag = false
//    private val Rm: FloatArray = FloatArray(9)
//    private val I: FloatArray = FloatArray(9)

    @Transient
    private var rotateAnimationCompass: RotateAnimation = RotateAnimation(
        currentCompasDegree,
        currentCompasDegree,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )

    @Transient
    private var rotateAnimationTarget: RotateAnimation = RotateAnimation(
        currentTargetDegree,
        currentTargetDegree,
        Animation.RELATIVE_TO_SELF, 0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    )

    @Bindable
    fun getRotationAnimationTarget(): RotateAnimation {
        return rotateAnimationTarget
    }

    @Bindable
    fun getRotationAnimationCompass(): RotateAnimation {
        return rotateAnimationCompass
    }

    @Transient
    private var lockUntil = System.currentTimeMillis() + 100
    override fun onSensorChanged(event: SensorEvent) {
        // if (lockUntil < System.currentTimeMillis()) {
//            //complex calculation method not depricated needs investigation
//            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
//                geomagnetic[0] = event.values[0]
//                geomagnetic[1] = event.values[1]
//                geomagnetic[2] = event.values[2]
//                havemag = true
//            }
//            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
//                gravity[0] = event.values[0]
//                gravity[1] = event.values[1]
//                gravity[2] = event.values[2]
//                haveacc = true
//            }
//            if (haveacc && havemag) {
//            lockUntil = System.currentTimeMillis() + 100
//        }

        // get the angle around the z-axis rotated


        // get the angle around the z-axis rotated
        val degree = -event.values[0].roundToInt().toFloat()

        rotateAnimationCompass = RotateAnimation(
            currentCompasDegree,
            degree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )


        // how long the animation will take place
        rotateAnimationCompass.duration = 100


        // set the animation after the end of the reservation status
        rotateAnimationCompass.fillAfter = true

        // create a rotation animation (reverse turn degree degrees)
        rotateAnimationTarget = RotateAnimation(
            currentCompasDegree + newTargetDegree,
            degree + newTargetDegree,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )


        // how long the animation will take place
        rotateAnimationTarget.duration = 100


        // set the animation after the end of the reservation status
        rotateAnimationTarget.fillAfter = true

        // Start the animation
        notifyPropertyChanged(BR.rotationAnimationCompass)
        notifyPropertyChanged(BR.rotationAnimationTarget)
        currentCompasDegree = degree
    }

    fun updateProperties() {
        notifyPropertyChanged(BR._all)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
    }

    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }


}