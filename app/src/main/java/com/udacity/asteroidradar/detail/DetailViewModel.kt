package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R

class DetailViewModel(asteroid: Asteroid, app: Application) : AndroidViewModel(app) {
    val resources = getApplication<Application>().resources

    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    init {
        _selectedAsteroid.value = asteroid
    }

    val selectedAsteroidDescription = when (_selectedAsteroid.value!!.isPotentiallyHazardous) {
        true -> resources.getString(R.string.potentially_hazardous_asteroid_image)
        else -> resources.getString(R.string.not_hazardous_asteroid_image)
    }

}