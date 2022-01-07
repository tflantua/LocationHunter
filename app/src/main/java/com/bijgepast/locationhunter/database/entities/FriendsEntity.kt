package com.bijgepast.locationhunter.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.bijgepast.locationhunter.models.BaseModel

@Entity(
    tableName = "Friends",
    primaryKeys = ["UserID1", "UserID2"],
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("ID"),
        childColumns = arrayOf("UserID1")
    ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("ID"),
            childColumns = arrayOf("UserID2")
        )]
)
data class FriendsEntity(
    @ColumnInfo(name = "UserID1") val firstUserID: String,
    @ColumnInfo(name = "UserID2") val secondUserID: String,
):BaseEntity {
    override fun getModel(): BaseModel {
        TODO("Not yet implemented")
    }
}