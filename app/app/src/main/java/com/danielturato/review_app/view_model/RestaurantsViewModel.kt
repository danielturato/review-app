package com.danielturato.review_app.view_model

import android.app.Application
import androidx.lifecycle.*
import com.danielturato.review_app.dto.Restaurant
import com.danielturato.review_app.livedata.LocationLiveData
import com.danielturato.review_app.dao.RestaurantsService
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class RestaurantsViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private val locationLiveData : LocationLiveData = LocationLiveData(application)
    fun getLocationLiveData() = locationLiveData

    private val restaurantsLiveData: LiveData<MutableSet<Restaurant>> =
        Transformations.switchMap(locationLiveData) { RestaurantsService.search(it).asLiveData() }
    fun getRestaurantsLiveData() = restaurantsLiveData


}