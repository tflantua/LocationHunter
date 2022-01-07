package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bijgepast.locationhunter.models.BaseModel

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey val ID: Int,
    @ColumnInfo(name = "Score") val Score: Int,
    @ColumnInfo(name = "UserName") val Name: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "key") val Key: String
): BaseEntity {
    override fun getModel(): BaseModel {
        TODO("Not yet implemented")
    }
}