package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.FormBody

class ApiHandler {
    companion object {
        private var instance: ApiHandler? = null
        fun getInstance(): ApiHandler {
            if (instance == null) instance = ApiHandler()

            return instance!!
        }
    }

    fun login(username: String, password: String, listener: CallbackListener) {
        Thread {
            val networkHandler = NetworkHandler.getInstance()
            val rb = FormBody.Builder()
                .add("userName", username)
                .add("password", password)
                .build()

            val response = networkHandler.POST("loginRequest.php", rb)
            if (response != null) this.loginHandler(response, listener)
            else listener.onFailure("Er is iets fout gegaan.")
        }.start()
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
}