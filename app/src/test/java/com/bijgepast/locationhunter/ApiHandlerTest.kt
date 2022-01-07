package com.bijgepast.locationhunter

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.interfaces.NetworkHandlerInterface
import com.bijgepast.locationhunter.utils.ApiHandler
import okhttp3.RequestBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

private const val FAKE_Response = "[{statusCode: 200 }, { Name: \"Thomas\", Score: 600, Key: \"fhjsljflasdjlfjlfjlksjfvj\"}]"
private const val FAKE_failedResponse = "[{statusCode: 401 }]"

private const val successString = "Logged in successfully"
private const val failString = "Logged in failed"

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {


    @Test
    fun login() {

        val mockedNetworkHandler = object : NetworkHandlerInterface{
            override fun POST(requestString: String, body: RequestBody): String? {
                return FAKE_Response
            }
        }

        var string = ""

        val callback = object : CallbackListener{
            override fun onSucces(obj: Any) {
                string = successString
            }

            override fun onFailure(obj: Any) {
                string = failString
            }

        }

        ApiHandler.getInstance(mockedNetworkHandler).login("Thomas", "Welkom01", callback)

        while (string.isEmpty())
        {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

    @Test
    fun loginFailed() {

        val mockedNetworkHandler = object : NetworkHandlerInterface{
            override fun POST(requestString: String, body: RequestBody): String? {
                return null
            }
        }

        var string = ""

        val callback = object : CallbackListener{
            override fun onSucces(obj: Any) {
               string = failString
            }

            override fun onFailure(obj: Any) {
                string = successString
            }

        }
        ApiHandler.getInstance(mockedNetworkHandler).login("Thomas", "Welkom01", callback)

        while (string.isEmpty())
        {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

    @Test
    fun loginUnauthorized() {

        val mockedNetworkHandler = object : NetworkHandlerInterface{
            override fun POST(requestString: String, body: RequestBody): String? {
                return FAKE_failedResponse
            }
        }

        var string = ""

        val callback = object : CallbackListener{
            override fun onSucces(obj: Any) {
                string = failString
            }

            override fun onFailure(obj: Any) {
                string = successString
            }

        }
        ApiHandler.getInstance(mockedNetworkHandler).login("Thomas", "Welkom01", callback)

        while (string.isEmpty())
        {
            Thread.sleep(100)
        }

        assertEquals(string, successString)
    }

}