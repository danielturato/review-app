package com.danielturato.review_app.dao

import com.danielturato.review_app.dto.Location
import com.danielturato.review_app.dto.Restaurant
import com.google.maps.GeoApiContext
import com.google.maps.PlacesApi
import com.google.maps.model.LatLng
import com.google.maps.model.PlaceType
import com.google.maps.model.PlacesSearchResponse
import com.google.maps.model.RankBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RestaurantsService {

    companion object {
        private val DEFAULT_RADIUS = 24140 // 15 miles

        /**
         * Search for restaurants based on a location.
         *
         * Uses the Google Place Nearby Search API to search for the
         * restaurants in a specific radius.
         */
        fun search(location: Location) : Flow<MutableSet<Restaurant>> {
            var searchRequest = PlacesSearchResponse()
            val context = GeoApiContext.Builder()
                .apiKey("AIzaSyBUez38KoBqbKnMlghrFRUUQWiM95Xv1vg")
                .build()
            val currLocation = LatLng(location.latitude.toDouble(), location.longitude.toDouble())

            searchRequest = PlacesApi.nearbySearchQuery(context, currLocation)
                .radius(DEFAULT_RADIUS)
                .rankby(RankBy.PROMINENCE)
                .type(PlaceType.RESTAURANT)
                .await()

            val restaurants = mutableSetOf<Restaurant>()

            for (r in searchRequest.results) {
                val restaurant =  Restaurant(r.placeId, r.name, r.formattedAddress,
                                             Location(r.geometry.location.lng.toString(),
                                                      r.geometry.location.lat.toString()))
                restaurants.add(restaurant)
            }

            return flowOf(restaurants)
        }
    }
}