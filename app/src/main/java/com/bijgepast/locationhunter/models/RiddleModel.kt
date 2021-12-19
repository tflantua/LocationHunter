package com.bijgepast.locationhunter.models

import android.hardware.*
import android.location.Location
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.utils.GpsManager
import com.bijgepast.locationhunter.utils.getStatusFromDistance
import java.io.Serializable
import kotlin.math.roundToInt


class RiddleModel(
    val locationModel: LocationModel,
    val RiddleName: String,
    val riddle: String,
    val difficulty: Int,
    val hints: List<HintModel>,
    private val points: Int,
    private var distanceStatus: DistanceStatus,
    private var completed: Boolean
) : BaseObservable(), Serializable, GpsManager.GpsCallback, SensorEventListener {

    private var declination: Float = 0f
    private var northDegrees: Float = 0f
    private var targetDegrees: Float = 0f
    private var currentDegree = 0f

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
        var points = this.points
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

    @Bindable
    fun getNorthDegrees(): Float {
        return this.northDegrees
    }

    private var currentRotation: Float = 0f

    @Bindable
    fun getCurrentDegrees(): Float {
        return currentDegree
    }

    private fun setCurrentRotation(degrees: Float) {
        if (degrees < 0 || degrees > 360)
            return
        this.currentDegree = degrees
        notifyPropertyChanged(BR.currentDegrees)
    }
    private var currentNorthRotation: Float = 0f

    @Bindable
    fun getCurrentNorthRotation(): Float {
        return currentNorthRotation
    }

    private fun setCurrentNorthRotation(degrees: Float) {
        if (degrees < 0 || degrees > 360)
            return
        this.currentNorthRotation = degrees
        notifyPropertyChanged(BR.currentNorthRotation)
    }

    private fun setNorthDegrees(degrees: Float) {
        if (degrees < 0 || degrees > 360)
            return
        this.northDegrees = degrees
        notifyPropertyChanged(BR.northDegrees)
    }

    @Bindable
    fun getTargetDegrees(): Float {
        return this.targetDegrees
    }

    private fun setTargetDegrees(degrees: Float) {
        if (degrees < 0 || degrees > 360)
            return
        this.targetDegrees = degrees
        notifyPropertyChanged(BR.targetDegrees)
    }


    override fun updateLocation(location: Location) {
        val floatArray: FloatArray = FloatArray(2)

        Location.distanceBetween(
            location.latitude,
            location.longitude,
            locationModel.north,
            locationModel.east,
            floatArray
        )

        val gmf = GeomagneticField(
            location.latitude.toFloat(),
            location.longitude.toFloat(),
            location.altitude.toFloat(),
            System.currentTimeMillis()
        )

        this.declination = gmf.declination

        this.setStatus(getStatusFromDistance(floatArray[0]))
        setCurrentRotation(this.targetDegrees)
        setCurrentNorthRotation(this.northDegrees)
        setTargetDegrees((floatArray[1]))
        setNorthDegrees(((this.currentDegree - this.declination)))
    }

    private val geomagnetic: FloatArray = FloatArray(3)
    private val gravity: FloatArray = FloatArray(3)
    private var haveacc = false
    private var havemag = false
    private val Rm: FloatArray = FloatArray(9)
    private val I: FloatArray = FloatArray(9)

    private val TwoPI = 2 * Math.PI


    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic[0] = event.values[0]
            geomagnetic[1] = event.values[1]
            geomagnetic[2] = event.values[2]
            havemag = true
        }
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            gravity[0] = event.values[0]
            gravity[1] = event.values[1]
            gravity[2] = event.values[2]
            haveacc = true
        }
        if (haveacc && havemag) {
            if (SensorManager.getRotationMatrix(
                    Rm,
                    I,
                    gravity,
                    geomagnetic
                )
            ) {
                val result = FloatArray(3)
                SensorManager.getOrientation(Rm, result)

                var res = result[0].toDouble()
                res = (res + TwoPI) % TwoPI

                res *= 180
                res /= Math.PI

                currentDegree = res.toFloat()

                setCurrentNorthRotation(this.northDegrees)
                setNorthDegrees(((this.currentDegree - this.declination)))
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        TODO("Not yet implemented")
    }


}