package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.interfaces.NetworkHandlerInterface
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.FormBody

class ApiHandler : LoadingAndSaving {
    companion object {
        private var instance: ApiHandler? = null
        private var networkHandler: NetworkHandlerInterface? = null
        fun getInstance(networkHandler: NetworkHandlerInterface): ApiHandler {
            this.networkHandler = networkHandler
            if (instance == null) instance = ApiHandler()

            return instance!!
        }
    }

    fun login(username: String, password: String, listener: CallbackListener) : Thread {
        val t = Thread {
            val rb = FormBody.Builder()
                .add("userName", username)
                .add("password", password)
                .build()

            val response = networkHandler?.POST("loginRequest.php", rb)
            if (response != null) this.loginHandler(response, listener)
            else listener.onFailure("Er is iets fout gegaan.")
        }
        t.start()
        return t
    }

    private fun loginHandler(jsonString: String, listener: CallbackListener) {
        val json: JsonArray = JsonParser.parseString(jsonString).asJsonArray
        val jsonStatus: JsonObject = json.get(0).asJsonObject

        if (jsonStatus.get("statusCode").asInt == 200) {
            val jsonUserInfo = json.get(1).asJsonObject
            listener.onSucces(jsonUserInfo)
        } else {
            listener.onFailure("StatusCode: ${jsonStatus.get("statusCode").asInt} \n Er is iets niet helemaal goed gegaan.")
        }
    }

    override fun getRiddles(): List<RiddleModel> {
        TODO("Not yet implemented")
    }

    override fun saveUnlocked(hintModel: HintModel) {
        TODO("Not yet implemented")
    }

    override fun saveVisited(riddleModel: RiddleModel) {
        TODO("Not yet implemented")
    }

    override fun saveFriends(id: Int, accept: Boolean) {
        TODO("Not yet implemented")
    }
}