package com.bijgepast.locationhunter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bijgepast.locationhunter.models.RiddleModel

class RiddleViewModel : ViewModel() {
    private var riddleModel: MutableLiveData<RiddleModel> = MutableLiveData<RiddleModel>()

    fun setRiddles(routeModel: RiddleModel) {
        riddleModel.value = routeModel
    }

    fun getRiddles(): LiveData<RiddleModel> {
        return riddleModel
    }
}