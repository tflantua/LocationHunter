package com.bijgepast.locationhunter.enums

enum class DistanceStatus(val DistanceInMeters: Int) {
    FROZEN(Int.MAX_VALUE),
    VERY_COLD(20000),
    COLD(15000),
    NORMAL(10000),
    HOT(5000),
    VERY_HOT(1000),
    HOTTER_THEN_HELL(500)
}