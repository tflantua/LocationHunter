package com.bijgepast.locationhunter.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService


class GpsManager(private val context: Activity) : LocationListener {
    private val listeners: MutableList<GpsCallback> = ArrayList()
    private val locationManager: LocationManager =
        getSystemService(context, LocationManager::class.java) as LocationManager

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10f, this)
    }

    fun addListener(listener: GpsCallback) {
        this.listeners.add(listener)
    }

    fun removeListener(listener: GpsCallback): Boolean {
        return this.listeners.remove(listener)
    }

    interface GpsCallback {
        fun updateLocation(long: Double, lat: Double)
    }

    override fun onLocationChanged(location: Location) {
        this.listeners.forEach { l -> l.updateLocation(location.longitude, location.latitude) }
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}