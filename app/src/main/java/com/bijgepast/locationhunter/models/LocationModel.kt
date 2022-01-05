package com.bijgepast.locationhunter.models

import com.bijgepast.locationhunter.database.entities.BaseEntity
import java.io.Serializable

class LocationModel(val north: Double, val east: Double, val Name: String): Serializable, BaseModel {
    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }
}