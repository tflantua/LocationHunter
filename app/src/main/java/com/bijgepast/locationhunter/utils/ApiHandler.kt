package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.interfaces.LoadingAndSaving
import com.bijgepast.locationhunter.interfaces.NetworkHandlerInterface
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel
import com.google.gson.Gson
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

    override fun login(username: String, password: String, listener: CallbackListener) {
        Thread {
            val rb = FormBody.Builder()
                .add("userName", username)
                .add("password", password)
                .build()

            val response = networkHandler?.POST("loginRequest.php", rb)
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

    override fun signup(username: String, password: String, listener: CallbackListener) {
        Thread {
            val rb = FormBody.Builder()
                .add("userName", username)
                .add("password", password)
                .build()

            val response = networkHandler?.POST("SignupRequest.php", rb)
            if (response != null) this.signupHandler(response, listener)
            else listener.onFailure("Er is iets fout gegaan.")
        }.start()
    }

    private fun signupHandler(jsonString: String, listener: CallbackListener) {
        val json: JsonArray = JsonParser.parseString(jsonString).asJsonArray
        val jsonStatus: JsonObject = json.get(0).asJsonObject

        if (jsonStatus.get("statusCode").asInt == 200) {
            val jsonUserInfo = json.get(1).asJsonObject
            listener.onSucces(jsonUserInfo)
        } else {
            listener.onFailure("StatusCode: ${jsonStatus.get("statusCode").asInt} \n Er is iets niet helemaal goed gegaan.")
        }
    }

    override fun getRiddles(key: String): List<RiddleModel>? {
        val response: String? =
            networkHandler?.POST(
                "getLocationsRequest.php",
                FormBody.Builder().add("Key", key).build()
            )
        if (response != null) {
            val jsonArray: JsonArray = JsonParser.parseString(response).asJsonArray
            val jsonStatus: StatusDataClass =
                Gson().fromJson(jsonArray[0].asJsonObject.toString(), StatusDataClass::class.java)
            return if (jsonStatus.statusCode == 200) {
                val responseArray: String = "{data: " + jsonArray[1].asJsonArray.toString() + "}"
                val responseData: ResponseData =
                    Gson().fromJson(responseArray, ResponseData::class.java)
                return responseData.data
            } else {
                null
            }
        }
        return ArrayList()

    }

    override fun saveUnlocked(hintModel: HintModel, key: String): Boolean {
        val response: String? =
            networkHandler?.POST(
                "saveUnlocked.php", FormBody.Builder().add("Key", key)
                    .add("HintId", hintModel.ID.toString())
                    .build()
            )
        if (response != null) {
            val jsonArray: JsonArray = JsonParser.parseString(response).asJsonArray
            val jsonStatus: StatusDataClass =
                Gson().fromJson(jsonArray[0].asJsonObject.toString(), StatusDataClass::class.java)
            return jsonStatus.statusCode == 200
        }
        return false
    }

    override fun saveVisited(riddleModel: RiddleModel, key: String): Boolean {
        val response: String? =
            networkHandler?.POST(
                "saveVisited.php", FormBody.Builder().add("Key", key)
                    .add("RiddleId", riddleModel.ID.toString())
                    .build()
            )
        if (response != null) {
            val jsonArray: JsonArray = JsonParser.parseString(response).asJsonArray
            val jsonStatus: StatusDataClass =
                Gson().fromJson(jsonArray[0].asJsonObject.toString(), StatusDataClass::class.java)
            return jsonStatus.statusCode == 200
        }
        return false
    }

//    override fun saveFriends(id: Int, accept: Boolean) {
//        TODO("Not yet implemented")
//    }
}