package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.parseDtoAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    val asteroidList: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAllAsteroids()) {
        it.asDomainModel()
    }
    // The suspend keyword is to to mark the function as coroutine-friendly
    suspend fun refreshDatabase() {
        // This is to make sure the DiskIO runs on the IO dispatcher so that it doesn't block the thread
        // Now the method can be called from any dispatcher and it'll still use the IO dispatcher
        // So it's super safe!
        withContext(Dispatchers.IO) {
            val asteroidResultString = AsteroidApi.retrofitService.getAsteroidProperties()
            val resultJsonObject = JSONObject(asteroidResultString)
            val asteroidList = parseDtoAsteroidsJsonResult(resultJsonObject)

            database.asteroidDao.insertAll(*asteroidList.asDatabaseModel().toTypedArray())
        }
    }
}