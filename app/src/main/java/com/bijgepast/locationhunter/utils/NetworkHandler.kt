package com.bijgepast.locationhunter.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class NetworkHandler {
    companion object {
        private var instance: NetworkHandler? = null
        fun getInstance(): NetworkHandler {
            if (instance == null) instance = NetworkHandler()

            return instance!!
        }

        const val address = "192.168.1.109"
    }

    private val client: OkHttpClient = OkHttpClient()
    private val address: String = "http://${NetworkHandler.address}/requests/"

    fun POST(requestString: String, body: RequestBody): String? {
        val request = Request.Builder()
            .url(this.address + requestString)
            .post(body)
            .build()

        return this.client.newCall(request).execute().body?.string()
    }
}