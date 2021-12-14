package com.bijgepast.locationhunter.enums

enum class DistanceStatus(val DistanceInMeters: UInt, val nameForEnum: String) {
    FROZEN(UInt.MAX_VALUE, "Frozen"),
    VERY_COLD(20000u, "Very cold"),
    COLD(15000u, "Cold"),
    ROOM_TEMPERATURE(10000u, "Room temperature"),
    HOT(5000u, "Hot"),
    VERY_HOT(1000u, "Very hot"),
    HOTTER_THEN_HELL(500u, "HOTTER THAN HELL")
}