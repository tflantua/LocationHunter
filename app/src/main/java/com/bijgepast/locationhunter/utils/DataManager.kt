package com.bijgepast.locationhunter.utils

import android.content.Context
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel
import okhttp3.RequestBody

class DataManager : LoadingAndSaving {
    companion object {
        private var instance: DataManager? = null
        fun getInstance(): DataManager {
            if (instance == null) instance = DataManager()

            return instance!!
        }

        private const val debugMode: Boolean = true
    }

    fun updateDatabase(){
        TODO("update local database with remote database")
    }

    override fun getRiddles(): List<RiddleModel> {
        //todo add some logic to check if remote riddles are the same as local
        if (!debugMode)
            return ApiHandler.getInstance().getRiddles()

        return DataBaseManager.getInstance().getRiddles()
    }

    override fun saveUnlocked(hintModel: HintModel) {
        //todo add some logic to check if remote riddles are the same as local
        if (!debugMode)
            ApiHandler.getInstance().saveUnlocked(hintModel)

        DataBaseManager.getInstance().saveUnlocked(hintModel)
    }

    override fun saveVisited(riddleModel: RiddleModel) {
        if (!debugMode)
            ApiHandler.getInstance().saveVisited(riddleModel)

        DataBaseManager.getInstance().saveVisited(riddleModel)
    }

    override fun saveFriends(id: Int, accept: Boolean) {
        if (!debugMode)
            ApiHandler.getInstance().saveFriends(id, accept)

        DataBaseManager.getInstance().saveFriends(id, accept)
    }


}