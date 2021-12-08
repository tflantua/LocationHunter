package com.bijgepast.locationhunter.utils

class ApiHandler {
    companion object {
        private var instance: ApiHandler? = null
        fun getInstance(): ApiHandler {
            if (instance == null) instance = ApiHandler()

            return instance!!
        }
    }


}