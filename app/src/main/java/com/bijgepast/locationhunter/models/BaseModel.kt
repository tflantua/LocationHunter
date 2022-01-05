package com.bijgepast.locationhunter.models

import com.bijgepast.locationhunter.database.entities.BaseEntity

interface BaseModel {
    fun getEntity(): BaseEntity
}