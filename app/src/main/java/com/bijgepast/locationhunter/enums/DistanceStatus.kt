package com.bijgepast.locationhunter.enums

enum class DistanceStatus(val DistanceInMeters: Float, val nameForEnum: String) {
    FROZEN(Float.MAX_VALUE, "Frozen"),
    VERY_COLD(20000f, "Very cold"),
    COLD(15000f, "Cold"),
    ROOM_TEMPERATURE(10000f, "Room temperature"),
    HOT(5000f, "Hot"),
    VERY_HOT(1000f, "Very hot"),
    HOTTER_THEN_HELL(500f, "HOTTER THAN HELL")
}