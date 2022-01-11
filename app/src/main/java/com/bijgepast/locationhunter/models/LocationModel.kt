package com.bijgepast.locationhunter.models

import com.bijgepast.locationhunter.database.entities.BaseEntity
import java.io.Serializable

class LocationModel(val north: Double, val east: Double, val name: String): Serializable, BaseModel {
    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }
}