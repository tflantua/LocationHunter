package com.bijgepast.locationhunter.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bijgepast.locationhunter.models.RiddleModel
import com.bijgepast.locationhunter.utils.GpsManager

class RiddleViewModel : ViewModel() {
    private var riddleModel: MutableLiveData<RiddleModel> = MutableLiveData<RiddleModel>()
    private var gpsManager: GpsManager? = null

    fun getGpsManager(): GpsManager? {
        return this.gpsManager
    }

    fun setGpsManager(gpsManager: GpsManager) {
        this.gpsManager = gpsManager
    }
    
    fun setRiddles(routeModel: RiddleModel) {
        riddleModel.value = routeModel
    }

    fun getRiddles(): LiveData<RiddleModel> {
        return riddleModel
    }
}