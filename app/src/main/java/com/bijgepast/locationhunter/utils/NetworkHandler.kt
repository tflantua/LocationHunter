package com.bijgepast.locationhunter.utils

import com.bijgepast.locationhunter.interfaces.NetworkHandlerInterface
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class NetworkHandler : NetworkHandlerInterface {
    companion object {
        private var instance: NetworkHandler? = null
        fun getInstance(): NetworkHandler {
            if (instance == null) instance = NetworkHandler()

            return instance!!
        }

        const val address = "143.178.48.211"
    }

    private val client: OkHttpClient = OkHttpClient()
    private val address: String = "http://${NetworkHandler.address}/requests/"

    override fun POST(requestString: String?, body: RequestBody?): String? {
        return try {
            val request = Request.Builder()
                .url(this.address + requestString)
                .post(body!!)
                .build()

            this.client.newCall(request).execute().body?.string()
        } catch (e: Exception) {
            null
        }
    }
}