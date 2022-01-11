package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.models.RiddleModel

data class StatusDataClass(
    val statusCode: Int
)

data class ResponseData(
    val data: List<RiddleModel>
)
