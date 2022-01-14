package com.bijgepast.locationhunter.utils

import android.content.Context
import androidx.room.Room
import com.bijgepast.locationhunter.database.AppDatabase
import com.bijgepast.locationhunter.database.dao.*
import com.bijgepast.locationhunter.database.entities.*
import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.interfaces.LoadingAndSaving
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.LocationModel
import com.bijgepast.locationhunter.models.RiddleModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import timber.log.Timber

class DataBaseManager() : LoadingAndSaving {
    companion object {
        private var instance: DataBaseManager? = null
        fun getInstance(): DataBaseManager {
            if (instance == null) instance = DataBaseManager()

            return instance!!
        }

        private var db: AppDatabase? = null
    }

    private var userDao: UserDao? = null
    private var hintsDao: HintsDao? = null
    private var friendsDao: FriendsDao? = null
    private var locationDao: LocationDao? = null
    private var unlockedHintsDao: UnlockedHintsDao? = null
    private var visitedLocationsDao: VisitedLocationsDao? = null

    private var context: Context? = null

    fun createDB(context: Context) {
        if (db != null) {
            Timber.d(DataBaseManager::class.simpleName.toString(), "Database already exists")
            return
        }

        this.context = context

        db = Room.databaseBuilder(context, AppDatabase::class.java, "location_hunter")
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

    //region data generation
    private fun generateDataIntoDatabase() {
        generateLocations()
        generateHints()
        generateUsers()
    }

    private fun generateUsers() {
        val userEntities = listOf<UserEntity>(
            UserEntity(0, 0, "ThomasFlantua", "Welkom 01", ""),
            UserEntity(1, 0, "RickBuring", "Welkom 03", "")
        )

        userEntities.forEach { user ->
            userDao?.insert(user)
        }

    }

    private fun generateHints() {
        val hintEntities = listOf(
            HintsEntity(0, 0, "noord positief oost positief zuid negatief west negatief", 100),
            HintsEntity(1, 0, "Null island", 400),

            HintsEntity(2, 1, "Tottenham Hotspur en Atlético Madrid, 1993", 100),
            HintsEntity(3, 1, "Bekent stadion Rotterdam", 400),

            HintsEntity(4, 2, "De cadeutjes fabriek van de kerstman staat?", 100),
            HintsEntity(5, 2, "de gemiddelde temperatuur is -16", 50),
            HintsEntity(6, 2, "Noord pool", 400),

            HintsEntity(7, 3, "Natuur gebied centraal nederland", 100),
            HintsEntity(8, 3, "BishBosh MuseumEiland", 400),
        )

        hintEntities.forEach { hint ->
            hintsDao?.insert(hint)
        }

    }

    private fun generateLocations() {
        val locationEntities = listOf(
            LocationEntity(0, 0.0, 0.0, "0.0", "Null point", "Null island", 600, 6),
            LocationEntity(
                1, 51.89401272565421, 4.523135398271194,
                "het mooiste stadion van Nederland", "Rood wit zwart", "De Kuip",
                300,
                3
            ),
            LocationEntity(2, 90.0, 0.0, "Cadeautjes", "Ho ho ho", "Noordpool", 600, 2),
            LocationEntity(
                3, 51.768064035055836, 4.77223399957273,
                "Ontdek hoe het zoetwatergetijdengebied ontstond na de Sint-Elisabethsvloed van 1421. " +
                        "De bewoners, hun economische activiteit, hun ambachten en de natuur: " +
                        "het komt allemaal aan bod in zintuigprikkelende ruimtes.",
                "bomen en water",
                "Biesbosch MuseumEiland",
                300,
                3
            )
        )

        locationEntities.forEach { location ->
            locationDao?.insert(location)
        }
    }
    //endregion

    override fun getRiddles(key: String): List<RiddleModel>? {
        val locations = locationDao?.getLocations()
        val riddleList: MutableList<RiddleModel> = ArrayList()
        val sharedPreferences = context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getInt("Id", 0)

        locations?.forEach { location ->
            val hintsLocation = hintsDao?.getHintsFromLocation(location.ID)
            val hintsList: MutableList<HintModel> = ArrayList()
            hintsLocation?.forEach { hint ->
                hintsList.add(
                    HintModel(
                        this.unlockedHintsDao?.getUnlocked(hint.ID, userId!!)!!,
                        hint.description,
                        hint.cost,
                        hint.ID
                    )
                )
            }

            riddleList.add(
                RiddleModel(
                    LocationModel(location.north, location.east, location.locationName),
                    location.riddleName,
                    location.riddle,
                    location.difficulty,
                    hintsList.toList(),
                    location.points,
                    DistanceStatus.FROZEN,
                    if (this.visitedLocationsDao?.getVisitedLocation(userId!!, location.ID) == null)
                        false
                    else
                        this.visitedLocationsDao?.getVisitedLocation(
                            userId!!,
                            location.ID
                        )!!.visited,
                    location.ID
                )
            )
        }

        return riddleList.toList()
    }

    override fun saveUnlocked(hintModel: HintModel, key: String): Boolean {
        Thread {
            val userEntity = userDao?.getUser(
                context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    ?.getString("Name", "UserName").toString()
            )
            val unlockedHintsEntity: UnlockedHintsEntity? =
                this.unlockedHintsDao?.getUnlockedHint(hintModel.ID, userEntity!!.ID)

            if (unlockedHintsEntity != null) {
                unlockedHintsEntity.unlocked = hintModel.getUnlocked()
                unlockedHintsDao?.update(
                    unlockedHintsEntity
                )
            } else {
                val unlocked =
                    UnlockedHintsEntity(userEntity!!.ID, hintModel.ID, hintModel.getUnlocked())
                this.unlockedHintsDao?.insert(unlocked)
            }
        }.start()
        return true
    }

    override fun saveVisited(riddleModel: RiddleModel, key: String): Boolean {
        Thread {
            val userEntity = userDao?.getUser(
                context?.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    ?.getString("Name", "UserName").toString()
            )
            val visitedLocationsEntity =
                this.visitedLocationsDao?.getVisitedLocation(userEntity!!.ID, riddleModel.ID)

            if (visitedLocationsEntity != null) {
                this.visitedLocationsDao?.update(
                    visitedLocationsEntity
                )
            } else {
                this.visitedLocationsDao?.insert(
                    VisitedLocationsEntity(
                        riddleModel.ID,
                        userEntity!!.ID,
                        riddleModel.getCompleted()
                    )
                )
            }

        }.start()
        return true
    }

//    override fun saveFriends(id: Int, accept: Boolean) {
//        TODO("Not yet implemented")
//    }

    override fun login(username: String, password: String, listener: CallbackListener) {
        Thread {
            val user: UserEntity? = this.userDao?.getUser(username)
            val allusers = this.userDao?.getAllUsers()
            if (user != null) {
                val checkPassword = user.password
                if (checkPassword == password) {
                    val jsonString: String = "data: " + Gson().toJson(user)
                    val jsonObject: JsonObject = JsonParser.parseString(jsonString).asJsonObject
                    listener.onSucces(jsonObject)
                } else {
                    listener.onFailure("Het wachtwoord komt niet overeen")
                }
            } else {
                listener.onFailure("De user bestaat niet")
            }
        }.start()
    }

    override fun signup(username: String, password: String, listener: CallbackListener) {
        if (this.userDao?.getUser(username) == null) {
            var Id = 0
            val allusers = this.userDao?.getAllUsers()
            allusers?.forEach { userEntity -> if (userEntity.ID >= Id) Id = userEntity.ID + 1 }

            this.userDao?.insert(
                UserEntity(
                    Id,
                    0,
                    username,
                    password,
                    (Math.random() * 1000 + Math.random()).toString()
                )
            )

            val user: UserEntity? = this.userDao?.getUser(username)
            if (user != null) {
                val jsonString: String = "data: " + Gson().toJson(user)
                val jsonObject: JsonObject = JsonParser.parseString(jsonString).asJsonObject
                listener.onSucces(jsonObject)
            } else {
                listener.onFailure("er ging iets mis")
            }
        } else {
            listener.onFailure("User bestaat al")
        }
    }

}