package com.bijgepast.locationhunter.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.bijgepast.locationhunter.BR
import com.bijgepast.locationhunter.enums.DistanceStatus
import java.io.Serializable

class RiddleModel(
    val locationModel: LocationModel,
    val RiddleName: String,
    val riddle: String,
    val difficulty: Int,
    private val hints: List<HintModel>,
    private val points: Int,
    private var distanceStatus: DistanceStatus
) : BaseObservable(), Serializable {

    private var selectedHint: Int = -1;

    @Bindable
    fun getHint(): HintModel? {
        return if (selectedHint > -1 && selectedHint < this.hints.size)
            this.hints[selectedHint]
        else null
    }

    @Bindable
    fun getSelectedHint(): Int {
        return selectedHint
    }

    @Bindable
    fun setSelectedHint(index: Int) {
        this.selectedHint = index
        notifyPropertyChanged(BR.selectedHint)
        notifyPropertyChanged(BR.hint)
    }

    @Bindable
    fun getPoints(): Int {
        var points = this.points;
        this.hints.forEach { hintModel ->
            if (hintModel.getUnlocked())
                points -= hintModel.cost
        }

        return 10.coerceAtLeast(points)
    }

    @Bindable
    fun getStatus(): DistanceStatus {
        return this.distanceStatus
    }

    @Bindable
    fun getStatusString(): String {
        return "Status: ${this.distanceStatus.nameForEnum}"
    }

    @Bindable
    fun setStatus(status: DistanceStatus) {
        this.distanceStatus = status
        notifyPropertyChanged(BR.status)
        notifyPropertyChanged(BR.statusString)
    }

}