package com.bijgepast.locationhunter.utils

import android.content.Context
import android.location.Location
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.map.OnMapReadyCallback
import com.tomtom.online.sdk.map.TomtomMap
import com.tomtom.online.sdk.routing.OnlineRoutingApi
import com.tomtom.online.sdk.routing.RoutingException
import com.tomtom.online.sdk.routing.route.RouteCallback
import com.tomtom.online.sdk.routing.route.RoutePlan
import com.tomtom.online.sdk.routing.route.RouteSpecification
import com.tomtom.online.sdk.search.OnlineSearchApi
import com.tomtom.online.sdk.search.SearchException
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcome
import com.tomtom.online.sdk.search.fuzzy.FuzzyOutcomeCallback
import com.tomtom.online.sdk.search.fuzzy.FuzzySearchSpecification
import com.tomtom.online.sdk.map.RouteBuilder


class TomTomManager(private val context: Context, private val gpsManager: GpsManager) :
    OnMapReadyCallback, GpsManager.GpsCallback, RouteCallback, FuzzyOutcomeCallback {
    private var tomtomMap: TomtomMap? = null

    override fun onMapReady(tomtomMap: TomtomMap) {
        this.tomtomMap = tomtomMap
        tomtomMap.isMyLocationEnabled = true

        gpsManager.addListener(this)
    }

    override fun updateLocation(location: Location) {
        tomtomMap?.centerOnMyLocation()
    }

    fun search(searchTerm: String) {
        val fuzzySearch = FuzzySearchSpecification.Builder(searchTerm).build()

        val searchApi = OnlineSearchApi.create(context, "AP1jNZ5uzFm201rNfeXVWSjXk1eIL394")

        searchApi.search(fuzzySearch, this)

    }

    fun planRoute(location: LatLng) {
        val routingApi =
            OnlineRoutingApi.create(context, "AP1jNZ5uzFm201rNfeXVWSjXk1eIL394")

        val route = RouteSpecification.Builder(LatLng(tomtomMap?.userLocation!!), location).build()

        routingApi.planRoute(route, this)
    }

    override fun onError(error: RoutingException) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(routePlan: RoutePlan) {
        val routeBuilder = RouteBuilder(routePlan.routes[0].getCoordinates())
        this.tomtomMap?.addRoute(routeBuilder)
    }

    override fun onError(error: SearchException) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(fuzzyOutcome: FuzzyOutcome) {
        planRoute(fuzzyOutcome.fuzzyDetailsList[0].position)
    }


}