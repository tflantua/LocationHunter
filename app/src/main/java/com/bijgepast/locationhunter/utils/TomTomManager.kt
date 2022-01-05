package com.bijgepast.locationhunter.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import androidx.core.content.res.ResourcesCompat
import com.bijgepast.locationhunter.R
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.location.LatLngBias
import com.tomtom.online.sdk.map.Icon
import com.tomtom.online.sdk.map.OnMapReadyCallback
import com.tomtom.online.sdk.map.RouteBuilder
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.RouteCallback
import com.tomtom.online.sdk.routing.route.RoutePlan
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.search.OnlineSearchApi
import com.tomtom.online.sdk.search.SearchException
import com.tomtom.online.sdk.search.fuzzy.FuzzyLocationDescriptor
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcome
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification


class TomTomManager(private val context: Context, private val gpsManager: GpsManager) :
    OnMapReadyCallback, GpsCallback, RouteCallback, FuzzyOutcomeCallback {
    private var tomtomMap: TomtomMap? = null
    private var location: Location? = null

    override fun onMapReady(tomtomMap: TomtomMap) {
        this.tomtomMap = tomtomMap
        tomtomMap.isMyLocationEnabled = true

        gpsManager.addListener(this)
    }

    override fun updateLocation(location: Location) {
        tomtomMap?.centerOnMyLocation()
        this.location = location
    }

    fun search(searchTerm: String, callback: FuzzyOutcomeCallback) {

        var fuzzySearch: FuzzySearchSpecification = if (this.location != null) {
            val fuzzyLocationDescriptor = FuzzyLocationDescriptor.Builder()
                .positionBias(LatLngBias(LatLng(location!!)))
                .build()

            FuzzySearchSpecification.Builder(searchTerm)
                .locationDescriptor(fuzzyLocationDescriptor)
                .build()
        } else
            FuzzySearchSpecification.Builder(searchTerm).build()

        val searchApi = OnlineSearchApi.create(context, "AP1jNZ5uzFm201rNfeXVWSjXk1eIL394")

        searchApi.search(fuzzySearch, callback)

    }

    fun planRoute(location: LatLng) {
        val routingApi =
            OnlineRoutingApi.create(context, "AP1jNZ5uzFm201rNfeXVWSjXk1eIL394")

        val route = RouteSpecification.Builder(LatLng(tomtomMap?.userLocation!!), location)
            .build()

        routingApi.planRoute(route, this)
    }

    override fun onError(error: RoutingException) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(routePlan: RoutePlan) {
        //todo fix for warning ? ResourcesCompat.getDrawable(context.resources, R.drawable.ic_twotone_location_on_24, context.resources.newTheme())
        val drawable = context.resources.getDrawable(R.drawable.ic_twotone_location_on_24)

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        val bitmapDrawable = BitmapDrawable(context.resources, bitmap)

        val routeBuilder = RouteBuilder(routePlan.routes[0].getCoordinates())
            .endIcon(Icon.Factory.fromDrawable("Endpoint", bitmapDrawable))
        this.tomtomMap?.addRoute(routeBuilder)
    }

    override fun onError(error: SearchException) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(fuzzyOutcome: FuzzyOutcome) {
        planRoute(fuzzyOutcome.fuzzyDetailsList[0].position)
    }


}