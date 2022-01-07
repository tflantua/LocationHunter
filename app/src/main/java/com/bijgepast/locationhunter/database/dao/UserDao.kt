package com.bijgepast.locationhunter.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bijgepast.locationhunter.database.entities.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {

    @Query("SELECT Score FROM user WHERE ID = :ID")
    fun getFriendScore(ID: Int): Int

    @Query("SELECT * FROM User WHERE UserName = :userName")
    fun getUser(userName: String): UserEntity?

    @Query("SELECT * FROM User WHERE UserName = :userName AND password = :password")
    fun login(userName: String, password: String): UserEntity?

}