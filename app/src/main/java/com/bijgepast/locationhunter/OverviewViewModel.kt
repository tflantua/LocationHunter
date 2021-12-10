package com.bijgepast.locationhunter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bijgepast.locationhunter.models.RiddleModel

class OverviewViewModel : ViewModel() {
    private var routes: MutableLiveData<List<RiddleModel>> = MutableLiveData<List<RiddleModel>>()

    fun setRiddles(routeModels: List<RiddleModel>) {
        routes.value = routeModels
    }

    fun getRiddles(): LiveData<List<RiddleModel>> {
        return routes
    }

}