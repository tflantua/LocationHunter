package com.bijgepast.locationhunter.database.entities

import androidx.room.Entity
import com.bijgepast.locationhunter.models.BaseModel

@Entity
interface BaseEntity {
    fun getModel() : BaseModel
}