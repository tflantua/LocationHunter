package com.bijgepast.locationhunter.enums

enum class DistanceStatus(val DistanceInMeters: UInt) {
    FROZEN(UInt.MAX_VALUE),
    VERY_COLD(20000u),
    COLD(15000u),
    NORMAL(10000u),
    HOT(5000u),
    VERY_HOT(1000u),
    HOTTER_THEN_HELL(500u)
}