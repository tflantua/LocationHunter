package com.bijgepast.locationhunter

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bijgepast.locationhunter.database.AppDatabase
import com.bijgepast.locationhunter.database.dao.*
import com.bijgepast.locationhunter.database.entities.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class EntitiyReadWriteTest {
    private lateinit var userDao: UserDao
    private lateinit var friendsDao: FriendsDao
    private lateinit var hintsDao: HintsDao
    private lateinit var locationDao: LocationDao
    private lateinit var unlockedHintsDao: UnlockedHintsDao
    private lateinit var visitedLocationsDao: VisitedLocationsDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        this.db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        this.userDao = db.userDao()
        this.friendsDao = db.friendsDao()
        this.hintsDao = db.hintsDao()
        this.locationDao = db.locationDao()
        this.unlockedHintsDao = db.unlockedHintsDao()
        this.visitedLocationsDao = db.visitedLocationsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        this.db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadUserFromDatabase() {
        val user = UserEntity(1, 0, "ThomasFlantua", "Welkom01", "")

        userDao.insert(user)
        val getUser = userDao.getUser("ThomasFlantua")

        Assert.assertEquals("De getUser komt niet overeen met de user", user, getUser)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadLocationFromDatabase() {
        val location = LocationEntity(1, 12.0, 15.10, "HOHOHO", "Kerstman", "Noordpool", 300, 6)

        locationDao.insert(location)
        val getLocation = locationDao.getLocation(1)

        Assert.assertEquals(
            "De getLocation komt niet overeen met de location",
            location,
            getLocation
        )
    }

    //<editor-fold desc="Hints">
    @Test
    @Throws(Exception::class)
    fun writeHintsFromDatabaseException() {
        val wrongHint = HintsEntity(2, 0, "Ja toch",300)

        var exception: java.lang.Exception? = null

        try {
            hintsDao.insert(wrongHint)
        } catch (e: java.lang.Exception) {
            exception = e
        }

        Assert.assertNotNull(exception)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadHintsFromDatabase() {
        writeAndReadLocationFromDatabase()

        val hint = HintsEntity(1, 1, "Ja toch", 300)
        hintsDao.insert(hint)

        val getHint = hintsDao.getTotalHints(1)
        Assert.assertEquals("Het aantal hints komt niet overeen", 1, getHint)
    }
    //</editor-fold>

    //<editor-fold desc="UnlockedHints">
    @Test
    @Throws(Exception::class)
    fun writeUnLockedHintsFromDatabaseException() {
        val wrongUnlockedHint = UnlockedHintsEntity(2, 0, false)

        var exception: java.lang.Exception? = null

        try {
            unlockedHintsDao.insert(wrongUnlockedHint)
        } catch (e: java.lang.Exception) {
            exception = e
        }

        Assert.assertNotNull(exception)
    }

    @Test
    @Throws(Exception::class)
    fun writeUnLockedHintsFromDatabase() {
        writeAndReadUserFromDatabase()
        writeAndReadHintsFromDatabase()

        val unlockedHint = UnlockedHintsEntity(1, 1, false)

        var exception: java.lang.Exception? = null

        try {
            unlockedHintsDao.insert(unlockedHint)
        } catch (e: java.lang.Exception) {
            exception = e
        }

        Assert.assertNull(exception)
    }
    //</editor-fold>

    //<editor-fold desc="VisitedLocation">
    @Test
    @Throws(Exception::class)
    fun writeVisitedLocationsFromDatabaseException() {
        val wrongVisitedLocation = VisitedLocationsEntity(2, 0, false)

        var exception: java.lang.Exception? = null

        try {
            visitedLocationsDao.insertVisitedLocation(wrongVisitedLocation)
        } catch (e: java.lang.Exception) {
            exception = e
        }

        Assert.assertNotNull(exception)
    }

    @Test
    @Throws(Exception::class)
    fun writeVisitedLocationFromDatabase() {
        writeAndReadUserFromDatabase()
        writeAndReadLocationFromDatabase()

        val visitedLocation = VisitedLocationsEntity(1, 1, false)

        var exception: java.lang.Exception? = null

        try {
            visitedLocationsDao.insertVisitedLocation(visitedLocation)
        } catch (e: java.lang.Exception) {
            exception = e
        }

        Assert.assertNull(exception)
    }
    //</editor-fold>
}