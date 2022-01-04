package com.bijgepast.locationhunter.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService

interface GpsCallback {
    fun updateLocation(location: Location)
}

class GpsManager(context: Activity) : LocationListener, GpsCallback {
    private val listeners: MutableList<GpsCallback> = ArrayList()
    private val locationManager: LocationManager =
        getSystemService(context, LocationManager::class.java) as LocationManager
    private var location: Location?

    init {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1f, this)

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null)
            onLocationChanged(location!!)

        addListener(this)
    }

    fun addListener(listener: GpsCallback) {
        this.listeners.add(listener)
        if (location != null)
            listener.updateLocation(location!!)
    }

    fun removeListener(listener: GpsCallback): Boolean {
        return this.listeners.remove(listener)
    }


    override fun onLocationChanged(location: Location) {
        this.listeners.forEach { l -> l.updateLocation(location) }
    }

    override fun updateLocation(location: Location) {
        this.location = location
    }
}