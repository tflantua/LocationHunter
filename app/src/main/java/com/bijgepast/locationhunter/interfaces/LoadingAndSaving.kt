package com.bijgepast.locationhunter.interfaces

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel

interface LoadingAndSaving {
    fun getRiddles(key: String) : List<RiddleModel>?
    fun saveUnlocked(hintModel: HintModel, key: String): Boolean
    fun saveVisited(riddleModel: RiddleModel, key: String): Boolean
    //fun saveFriends(id: Int, accept: Boolean)
    fun login(username: String, password: String, listener: CallbackListener)
    fun signup(username: String, password: String, listener: CallbackListener)
}