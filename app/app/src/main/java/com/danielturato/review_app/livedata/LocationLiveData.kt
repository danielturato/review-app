package com.danielturato.review_app.livedata

import android.content.Context
import androidx.lifecycle.LiveData
import com.danielturato.review_app.dto.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class LocationLiveData(context: Context) : LiveData<Location>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    override fun onActive() {
        super.onActive()
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onInactive() {
        super.onInactive()
        // If no observer(s) , turn off location updates
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult ?: return

            for (location in locationResult.locations) {
                setLocation(location)
            }
        }
    }

    private fun setLocation(location: android.location.Location) {
        value = Location(location.longitude.toString(), location.latitude.toString())
    }

    companion object {
        const val ONE_MIN : Long = 60000

        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = ONE_MIN
            fastestInterval = ONE_MIN / 4
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

}