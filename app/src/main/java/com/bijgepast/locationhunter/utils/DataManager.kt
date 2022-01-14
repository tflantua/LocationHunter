package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.interfaces.LoadingAndSaving
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel

class DataManager : LoadingAndSaving {
    companion object {
        private var instance: DataManager? = null
        fun getInstance(): DataManager {
            if (instance == null) instance = DataManager()

            return instance!!
        }

        private val debugMode: Boolean = true
    }

    fun updateDatabase() {
        TODO("update local database with remote database")
    }

    override fun getRiddles(key: String): List<RiddleModel>? {
        return if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).getRiddles(key)
        else
            DataBaseManager.getInstance().getRiddles(key)
    }

    override fun saveUnlocked(hintModel: HintModel, key: String): Boolean {
        return if (!debugMode) {
            ApiHandler.getInstance(NetworkHandler.getInstance()).saveUnlocked(hintModel, key)
        } else
            DataBaseManager.getInstance().saveUnlocked(hintModel, key)
    }

    override fun saveVisited(riddleModel: RiddleModel, key: String): Boolean {
        return if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).saveVisited(riddleModel, key)
        else
            DataBaseManager.getInstance().saveVisited(riddleModel, key)
    }

//    override fun saveFriends(id: Int, accept: Boolean) {
//        if (!debugMode)
//            ApiHandler.getInstance(NetworkHandler.getInstance()).saveFriends(id, accept)
//        else
//            DataBaseManager.getInstance().saveFriends(id, accept)
//    }

    override fun login(username: String, password: String, listener: CallbackListener) {
        if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).login(username, password, listener)
        else
            DataBaseManager.getInstance().login(username, password, listener)
    }

    override fun signup(username: String, password: String, listener: CallbackListener) {
        if(!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).signup(username, password, listener)
            else
            DataBaseManager.getInstance().signup(username, password, listener)
    }


}