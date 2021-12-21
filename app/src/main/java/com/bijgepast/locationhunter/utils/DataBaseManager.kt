package com.bijgepast.locationhunter.utils

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bijgepast.locationhunter.database.AppDatabase
import com.bijgepast.locationhunter.database.dao.*
import com.bijgepast.locationhunter.database.entities.HintsEntity
import com.bijgepast.locationhunter.database.entities.LocationEntity
import com.bijgepast.locationhunter.database.entities.UserEntity

class DataBaseManager() {
    companion object {
        private var instance: DataBaseManager? = null
        fun getInstance(): DataBaseManager {
            if (instance == null) instance = DataBaseManager()

            return instance!!
        }
    }

    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var hintsDao: HintsDao? = null
    private var friendsDao: FriendsDao? = null
    private var locationDao: LocationDao? = null
    private var unlockedHintsDao: UnlockedHintsDao? = null
    private var visitedLocationsDao: VisitedLocationsDao? = null

    fun createDB(context: Context) {
        if (db != null) {
            Log.d(DataBaseManager::class.simpleName.toString(), "Database already exists")
            return
        }

        this.db = Room.databaseBuilder(context, AppDatabase::class.java, "Location hunter")
            .createFromAsset("database/location_hunter.db")
            .build()

        this.userDao = db?.userDao()
        this.hintsDao = db?.hintsDao()
        this.friendsDao = db?.friendsDao()
        this.locationDao = db?.locationDao()
        this.unlockedHintsDao = db?.unlockedHintsDao()
        this.visitedLocationsDao = db?.visitedLocationsDao()

        if (locationDao!!.getLocations().isEmpty())
            generateDataIntoDatabase()
    }

    private fun generateDataIntoDatabase() {
        GenerateLocations()
        GenerateHints()
        GenerateUsers()
    }

    private fun GenerateUsers() {
        val userEntities = listOf<UserEntity>(
            UserEntity(0, 0, "ThomasFlantua", "Welkom 01", ""),
            UserEntity(1, 0, "RickBuring", "Welkom 03", "")
        )

        userEntities.forEach { user ->
            userDao?.insertUser(user)
        }

    }

    private fun GenerateHints() {
        val hintEntities = listOf(
            HintsEntity(0, 1, "noord positief oost positief zuid negatief west negatief", 100),
            HintsEntity(1, 1, "Null island", 400),

            HintsEntity(0, 2, "Tottenham Hotspur en Atlético Madrid, 1993", 100),
            HintsEntity(1, 2, "Bekent stadion Rotterdam", 400),

            HintsEntity(0, 3, "De cadeutjes fabriek van de kerstman staat?", 100),
            HintsEntity(1, 3, "de gemiddelde temperatuur is -16", 50),
            HintsEntity(2, 3, "Noord pool", 400),

            HintsEntity(0, 4, "Natuur gebied centraal nederland", 100),
            HintsEntity(1, 4, "BishBosh MuseumEiland", 400),
        )

        hintEntities.forEach { hint ->
            hintsDao?.insertHint(hint)
        }

    }

    private fun GenerateLocations() {
        val locationEntities = listOf(
            LocationEntity(0, 0.0, 0.0, "0.0", "Null point", "Null island"),
            LocationEntity(
                1, 51.89401272565421, 4.523135398271194,
                "het mooiste stadion van Nederland", "Rood wit zwart", "De Kuip"
            ),
            LocationEntity(2, 90.0, 0.0, "Cadeautjes", "Ho ho ho", "Noordpool"),
            LocationEntity(
                3, 51.768064035055836, 4.77223399957273,
                "Ontdek hoe het zoetwatergetijdengebied ontstond na de Sint-Elisabethsvloed van 1421. " +
                        "De bewoners, hun economische activiteit, hun ambachten en de natuur: " +
                        "het komt allemaal aan bod in zintuigprikkelende ruimtes.",
                "bomen en water",
                "Biesbosch MuseumEiland"
            )
        )

        locationEntities.forEach { location ->
            locationDao?.insertLocation(location)
        }
    }


}