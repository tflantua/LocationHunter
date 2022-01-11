package com.bijgepast.locationhunter.models

import com.bijgepast.locationhunter.database.entities.BaseEntity

data class FriendModel(val userName: String, val score: Int, val userId: Int) : BaseModel {
    
    override fun getEntity(): BaseEntity {
        TODO("Not yet implemented")
    }
}