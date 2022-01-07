package com.bijgepast.locationhunter.interfaces

import okhttp3.RequestBody

interface NetworkHandlerInterface {
    fun POST(requestString: String, body: RequestBody): String?
}