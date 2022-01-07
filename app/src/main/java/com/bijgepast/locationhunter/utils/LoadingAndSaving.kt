package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.CallbackListener
import com.bijgepast.locationhunter.models.HintModel
import com.bijgepast.locationhunter.models.RiddleModel

interface LoadingAndSaving {
    fun getRiddles() : List<RiddleModel>
    fun saveUnlocked(hintModel: HintModel)
    fun saveVisited(riddleModel: RiddleModel)
    fun saveFriends(id: Int, accept: Boolean)
    fun login(username: String, password: String, listener: CallbackListener)
}