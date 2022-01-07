package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel

class DataManager : LoadingAndSaving {
    companion object {
        private var instance: DataManager? = null
        fun getInstance(): DataManager {
            if (instance == null) instance = DataManager()

            return instance!!
        }

        private const val debugMode: Boolean = true
    }

    fun updateDatabase() {
        TODO("update local database with remote database")
    }

    override fun getRiddles(): List<RiddleModel> {
        //todo add some logic to check if remote riddles are the same as local
        if (!debugMode)
            return ApiHandler.getInstance(NetworkHandler.getInstance()).getRiddles()

        return DataBaseManager.getInstance().getRiddles()
    }

    override fun saveUnlocked(hintModel: HintModel) {
        if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).saveUnlocked(hintModel)

        DataBaseManager.getInstance().saveUnlocked(hintModel)
    }

    override fun saveVisited(riddleModel: RiddleModel) {
        if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).saveVisited(riddleModel)

        DataBaseManager.getInstance().saveVisited(riddleModel)
    }

    override fun saveFriends(id: Int, accept: Boolean) {
        if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).saveFriends(id, accept)

        DataBaseManager.getInstance().saveFriends(id, accept)
    }

    override fun login(username: String, password: String, listener: CallbackListener) {
        if (!debugMode)
            ApiHandler.getInstance(NetworkHandler.getInstance()).login(username, password, listener)
        else
            DataBaseManager.getInstance().login(username, password, listener)
    }


}