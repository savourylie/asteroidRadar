package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.parseImageOfTheDayJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

enum class ApiStatus {LOADING, ERROR, DONE}

class MainViewModel : ViewModel() {
    private val _imgOfDayStatus = MutableLiveData<ApiStatus>()
    val imgOfDayStatus: LiveData<ApiStatus>
        get() = _imgOfDayStatus

    private val _imgOfDayProperties = MutableLiveData<PictureOfDay>()
    val imgOfDayProperties: LiveData<PictureOfDay>
        get() = _imgOfDayProperties

    private val _asteroidStatus = MutableLiveData<ApiStatus>()
    val asteroidStatus: LiveData<ApiStatus>
        get() = _asteroidStatus

    private val _asteroidProperties = MutableLiveData<List<Asteroid>>()
    val asteroidProperties: LiveData<List<Asteroid>>
        get() = _asteroidProperties

    init {
        getImageOfTheDay()
        getAsteroidProperties()
    }

    private fun getImageOfTheDay() {
        viewModelScope.launch {
            _imgOfDayStatus.value = ApiStatus.LOADING

            try {
                var resultString = AsteroidApi.retrofitService.getImageOfTheDay()
                _imgOfDayStatus.value = ApiStatus.DONE
                val resultJsonObject = JSONObject(resultString)
                val pictureOfDay = parseImageOfTheDayJsonResult(resultJsonObject)

                _imgOfDayProperties.value = pictureOfDay

                Log.d("HTTP", pictureOfDay.url)

            } catch (e: Exception) {
                _imgOfDayStatus.value = ApiStatus.ERROR
                _imgOfDayProperties.value = null
            }
        }
    }

    private fun getAsteroidProperties() {
        viewModelScope.launch {
            _asteroidStatus.value = ApiStatus.LOADING

            try {
                var resultString = AsteroidApi.retrofitService.getAsteroidProperties()
                _asteroidStatus.value = ApiStatus.DONE
                val resultJsonObject = JSONObject(resultString)
                val asteroidList = parseAsteroidsJsonResult(resultJsonObject)

                if (asteroidList.size > 0) {
                    _asteroidProperties.value = asteroidList
                }
            } catch (e: Exception) {
                _asteroidStatus.value = ApiStatus.ERROR
                _asteroidProperties.value = null
            }
        }
    }

    private val _navigateToAsteroidDetail = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToAsteroidDetail

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetail.value = asteroid
    }

    fun onAsteroidDetailNavigated() {
        _navigateToAsteroidDetail.value = null
    }
}