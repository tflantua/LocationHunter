package com.bijgepast.locationhunter.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bijgepast.locationhunter.database.dao.*
import com.bijgepast.locationhunter.database.entities.*

@Database(
    entities = [FriendsEntity::class, HintsEntity::class, LocationEntity::class, UnlockedHintsEntity::class,
        UserEntity::class, VisitedLocationsEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun friendsDao(): FriendsDao
    abstract fun hintsDao(): HintsDao
    abstract fun locationDao(): LocationDao
    abstract fun unlockedHintsDao(): UnlockedHintsDao
    abstract fun userDao(): UserDao
    abstract fun visitedLocationsDao(): VisitedLocationsDao
}