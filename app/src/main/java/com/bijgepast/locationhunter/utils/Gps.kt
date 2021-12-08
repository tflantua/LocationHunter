package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.enums.DistanceStatus

fun getStatusFromDistance(distance: UInt): DistanceStatus {
    var distanceStatus: DistanceStatus = DistanceStatus.FROZEN;
    DistanceStatus.values().forEach { v ->
        if (distance <= v.DistanceInMeters) {
            distanceStatus = v
        } else
            return distanceStatus;
    }

    return distanceStatus;
}
