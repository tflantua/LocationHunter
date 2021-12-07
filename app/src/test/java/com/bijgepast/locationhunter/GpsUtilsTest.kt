package com.bijgepast.locationhunter

import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.utils.getStatusFromDistance
import org.junit.Test

class GpsUtilsTest {

    @Test
    fun DistanceTest() {
        assert(getStatusFromDistance(DistanceStatus.FROZEN.DistanceInMeters) == DistanceStatus.FROZEN)

        assert(getStatusFromDistance(DistanceStatus.VERY_COLD.DistanceInMeters) == DistanceStatus.VERY_COLD)

        assert(getStatusFromDistance(DistanceStatus.COLD.DistanceInMeters) == DistanceStatus.COLD)

        assert(getStatusFromDistance(DistanceStatus.NORMAL.DistanceInMeters) == DistanceStatus.NORMAL)

        assert(getStatusFromDistance(DistanceStatus.HOT.DistanceInMeters) == DistanceStatus.HOT)

        assert(getStatusFromDistance(DistanceStatus.VERY_HOT.DistanceInMeters) == DistanceStatus.VERY_HOT)

        assert(getStatusFromDistance(DistanceStatus.HOTTER_THEN_HELL.DistanceInMeters) == DistanceStatus.HOTTER_THEN_HELL)
    }
}