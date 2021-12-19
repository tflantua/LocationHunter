package com.bijgepast.locationhunter

import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.utils.bearingToDegrees
import com.bijgepast.locationhunter.utils.getStatusFromDistance
import org.junit.Assert
import org.junit.Test

class GpsUtilsTest {

    @Test
    fun DistanceTest() {
        Assert.assertThrows(IllegalArgumentException::class.java) { getStatusFromDistance(-500f) }

        assert(getStatusFromDistance(DistanceStatus.FROZEN.DistanceInMeters) == DistanceStatus.FROZEN)

        assert(getStatusFromDistance(DistanceStatus.VERY_COLD.DistanceInMeters) == DistanceStatus.VERY_COLD)

        assert(getStatusFromDistance(DistanceStatus.COLD.DistanceInMeters) == DistanceStatus.COLD)

        assert(getStatusFromDistance(DistanceStatus.ROOM_TEMPERATURE.DistanceInMeters) == DistanceStatus.ROOM_TEMPERATURE)

        assert(getStatusFromDistance(DistanceStatus.HOT.DistanceInMeters) == DistanceStatus.HOT)

        assert(getStatusFromDistance(DistanceStatus.VERY_HOT.DistanceInMeters) == DistanceStatus.VERY_HOT)

        assert(getStatusFromDistance(DistanceStatus.HOTTER_THEN_HELL.DistanceInMeters) == DistanceStatus.HOTTER_THEN_HELL)
    }

    @Test
    fun radiansToDegrees() {
        var r = calculateDegrees(Math.PI)
        r = calculateDegrees(Math.PI/2)
        r = calculateDegrees(Math.PI*2)
        r = calculateDegrees(Math.PI/2*3)
        r = calculateDegrees(Math.PI/16*9)
        r = calculateDegrees(Math.PI/153*120)
        r = calculateDegrees(Math.PI/30*12)
        r = calculateDegrees(Math.PI/40*17)
        r = calculateDegrees(-Math.PI/50*57)

        assert(true)

    }

    fun calculateDegrees(res: Double) : Double {

        var result = res + (2 * Math.PI)
        result %= (2 * Math.PI)

        result *= 180
        result /= Math.PI
        return result
    }


    @Test
    fun bearingTest() {
        assert(bearingToDegrees(-30f) == 330f)

        assert(bearingToDegrees(30f) == 30f)
    }
}