package com.bijgepast.locationhunter.interfaces

import androidx.annotation.Nullable

interface CallbackListener {
    fun onSucces(@Nullable obj: Any)

    fun onFailure(@Nullable obj: Any)
}