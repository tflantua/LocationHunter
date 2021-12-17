package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.enums.DistanceStatus
import java.lang.IllegalArgumentException

fun getStatusFromDistance(distance: Float): DistanceStatus {
    if (distance < 0)
        throw IllegalArgumentException("distance cant be negative")

    var distanceStatus: DistanceStatus = DistanceStatus.FROZEN;
    DistanceStatus.values().forEach { v ->
        if (distance <= v.DistanceInMeters) {
            distanceStatus = v
        } else
            return distanceStatus;
    }

    return distanceStatus;
}

fun bearingToDegrees(bearing: Float): Float {
    return (bearing + 360) % 360
}
