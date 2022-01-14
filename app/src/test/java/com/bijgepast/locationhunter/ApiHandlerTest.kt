package com.bijgepast.locationhunter

import com.bijgepast.locationhunter.enums.DistanceStatus
import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.interfaces.NetworkHandlerInterface
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.LocationModel
import com.bijgepast.locationhunter.models.RiddleModel
import com.bijgepast.locationhunter.utils.ApiHandler
import okhttp3.RequestBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

private const val FAKE_LOGIN_SUCCESSFUL_RESPONSE =
    "[{statusCode: 200 }, { Name: \"Thomas\", Score: 600, Key: \"fhjsljflasdjlfjlfjlksjfvj\"}]"
private const val FAKE_UNAUTHORIZED_RESPONSE = "[{statusCode: 401 }]"
private const val FAKE_USER_ALREADY_EXISTS = "[{statusCode: 104 }]"

private const val FAKE_ResponseForLocations = "[{\"statusCode\":200}," +
        "[{\"ID\":\"2\",\"north\":\"0.000000\",\"east\":\"0.000000\",\"name\":\"NULL island\",\"riddleName\":\"NULL\",\"riddle\":\"0.0\",\"points\":\"400\",\"difficulty\":\"5\",\"hintsList\":[{\"hint\":\"Boven positief, onder negatief, rechts negatief, links positief\",\"ID\":\"3\",\"cost\":\"20\",\"unlocked\":false}],\"visited\":false},{\"ID\":\"1\",\"north\":\"51.893710\",\"east\":\"4.523190\",\"name\":\"De kuip\",\"riddleName\":\"\",\"riddle\":\"Het mooiste stadion van Nederland\",\"points\":\"0\",\"difficulty\":\"0\",\"hintsList\":[{\"hint\":\"Tottenham Hotspur en Atl\\u00e9tico Madrid, 1993\",\"ID\":\"1\",\"cost\":\"100\",\"unlocked\":true},{\"hint\":\"Bekend stadion Rotterdam\",\"ID\":\"2\",\"cost\":\"100\",\"unlocked\":false}],\"visited\":true}]]"

private const val successString = "Logged in successfully"
private const val failString = "Logged in failed"

@RunWith(MockitoJUnitRunner::class)
class ApiHandlerTest {
    @Mock
    private lateinit var mockNetworkHander2: NetworkHandlerInterface
    private lateinit var apiHandler: ApiHandler

    private var mockRiddleModel: RiddleModel = RiddleModel(
        LocationModel(0.0, 0.0, "Hoi"),
        "Test",
        "Dit is een test",
        6,
        ArrayList(),
        200,
        DistanceStatus.FROZEN,
        false,
        0
    )

    private var mockHintModel: HintModel = HintModel(false, "dit is een hint", 200, 2)

    //<editor-fold desc="Login">
    @Test
    fun login() {

        val mockedNetworkHandler = object : NetworkHandlerInterface {
            override fun POST(requestString: String?, body: RequestBody?): String? {
                return FAKE_LOGIN_SUCCESSFUL_RESPONSE
            }
        }

        var string = ""

        val callback = object : CallbackListener {
            override fun onSucces(obj: Any) {
                string = successString
            }

            override fun onFailure(obj: Any) {
                string = failString
            }

        }

        ApiHandler.getInstance(mockedNetworkHandler).login("Thomas", "Welkom01", callback)

        while (string.isEmpty()) {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

    @Test
    fun loginFailed() {

        val mockedNetworkHandler = object : NetworkHandlerInterface {
            override fun POST(requestString: String?, body: RequestBody?): String? {
                return null
            }
        }

        var string = ""

        val callback = object : CallbackListener {
            override fun onSucces(obj: Any) {
                string = failString
            }

            override fun onFailure(obj: Any) {
                string = successString
            }

        }
        ApiHandler.getInstance(mockedNetworkHandler).login("Thomas", "Welkom01", callback)

        while (string.isEmpty()) {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

    @Test
    fun loginUnauthorized() {

        val mockedNetworkHandler = object : NetworkHandlerInterface {
            override fun POST(requestString: String?, body: RequestBody?): String? {
                return FAKE_UNAUTHORIZED_RESPONSE
            }
        }

        var string = ""

        val callback = object : CallbackListener {
            override fun onSucces(obj: Any) {
                string = failString
            }

            override fun onFailure(obj: Any) {
                string = successString
            }

        }
        ApiHandler.getInstance(mockedNetworkHandler).login("Thomas", "Welkom01", callback)

        while (string.isEmpty()) {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }
    //</editor-fold>

    //<editor-fold desc="Sign up">
    @Test
    fun signup() {

        val mockedNetworkHandler = object : NetworkHandlerInterface {
            override fun POST(requestString: String?, body: RequestBody?): String? {
                return FAKE_LOGIN_SUCCESSFUL_RESPONSE
            }
        }

        var string = ""

        val callback = object : CallbackListener {
            override fun onSucces(obj: Any) {
                string = successString
            }

            override fun onFailure(obj: Any) {
                string = failString
            }

        }

        ApiHandler.getInstance(mockedNetworkHandler).signup("Thomas", "Welkom01", callback)

        while (string.isEmpty()) {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

    @Test
    fun signupFailed() {

        val mockedNetworkHandler = object : NetworkHandlerInterface {
            override fun POST(requestString: String?, body: RequestBody?): String? {
                return null
            }
        }

        var string = ""

        val callback = object : CallbackListener {
            override fun onSucces(obj: Any) {
                string = failString
            }

            override fun onFailure(obj: Any) {
                string = successString
            }

        }
        ApiHandler.getInstance(mockedNetworkHandler).signup("Thomas", "Welkom01", callback)

        while (string.isEmpty()) {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

    @Test
    fun signupFailedUserAlreadyExist() {

        val mockedNetworkHandler = object : NetworkHandlerInterface {
            override fun POST(requestString: String?, body: RequestBody?): String? {
                return FAKE_USER_ALREADY_EXISTS
            }
        }

        var string = ""

        val callback = object : CallbackListener {
            override fun onSucces(obj: Any) {
                string = failString
            }

            override fun onFailure(obj: Any) {
                string = successString
            }

        }
        ApiHandler.getInstance(mockedNetworkHandler).signup("Thomas", "Welkom01", callback)

        while (string.isEmpty()) {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }
    //</editor-fold>

    //<editor-fold desc="getRiddles">
    @Test
    fun getRiddlesWithCorrectKey() {
        `when`(mockNetworkHander2.POST(eq("getLocationsRequest.php"), any(RequestBody::class.java)))
            .thenReturn(FAKE_ResponseForLocations)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: List<RiddleModel>? = apiHandler.getRiddles("CORRECT_KEY")

        assertEquals(2, result?.size)
    }

    @Test
    fun getRiddlesWithUncorrectKey() {
        `when`(mockNetworkHander2.POST(eq("getLocationsRequest.php"), any(RequestBody::class.java)))
            .thenReturn(FAKE_UNAUTHORIZED_RESPONSE)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: List<RiddleModel>? = apiHandler.getRiddles("INCORRECT_KEY")

        assertNull(result)
    }

    @Test
    fun getRiddlesWithReturnNull() {
        `when`(mockNetworkHander2.POST(eq("getLocationsRequest.php"), any(RequestBody::class.java)))
            .thenReturn(null)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: List<RiddleModel>? = apiHandler.getRiddles("CORRECT_KEY")

        assertEquals(0, result?.size)
    }
    //</editor-fold>

    //<editor-fold desc="saveVisited">
    @Test
    fun saveVisitedWithCorrectKey() {
        `when`(mockNetworkHander2.POST(eq("saveVisited.php"), any(RequestBody::class.java)))
            .thenReturn(FAKE_ResponseForLocations)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: Boolean = apiHandler.saveVisited(mockRiddleModel, "CORRECT_KEY")

        assertEquals(true, result)
    }

    @Test
    fun saveVisitedUncorrectKey() {
        `when`(mockNetworkHander2.POST(eq("saveVisited.php"), any(RequestBody::class.java)))
            .thenReturn(FAKE_UNAUTHORIZED_RESPONSE)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: Boolean = apiHandler.saveVisited(mockRiddleModel, "INCORRECT_KEY")

        assertEquals(false, result)
    }

    @Test
    fun saveVisitedWithReturnNull() {
        `when`(mockNetworkHander2.POST(eq("saveVisited.php"), any(RequestBody::class.java)))
            .thenReturn(null)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: Boolean = apiHandler.saveVisited(mockRiddleModel, "CORRECT_KEY")

        assertEquals(false, result)
    }
    //</editor-fold>

    //<editor-fold desc="saveUnlocked">
    @Test
    fun saveUnlockedWithCorrectKey() {
        `when`(mockNetworkHander2.POST(eq("saveUnlocked.php"), any(RequestBody::class.java)))
            .thenReturn(FAKE_ResponseForLocations)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: Boolean = apiHandler.saveUnlocked(mockHintModel, "CORRECT_KEY")

        assertEquals(true, result)
    }

    @Test
    fun saveUnlockedWithUncorrectKey() {
        `when`(mockNetworkHander2.POST(eq("saveUnlocked.php"), any(RequestBody::class.java)))
            .thenReturn(FAKE_UNAUTHORIZED_RESPONSE)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: Boolean = apiHandler.saveUnlocked(mockHintModel, "CORRECT_KEY")

        assertEquals(false, result)
    }

    @Test
    fun saveUnlockedWithReturnNull() {
        `when`(mockNetworkHander2.POST(eq("saveUnlocked.php"), any(RequestBody::class.java)))
            .thenReturn(null)

        apiHandler = ApiHandler.getInstance(mockNetworkHander2)
        val result: Boolean = apiHandler.saveUnlocked(mockHintModel, "CORRECT_KEY")

        assertEquals(false, result)
    }
    //</editor-fold>
}