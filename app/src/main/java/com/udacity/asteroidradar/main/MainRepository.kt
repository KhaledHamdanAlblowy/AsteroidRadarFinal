package com.udacity.asteroidradar.main

import androidx.annotation.WorkerThread
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Utils.getCurrentDate
import com.udacity.asteroidradar.Utils.getFutureDate
import com.udacity.asteroidradar.api.AsteriodAPIClient
import com.udacity.asteroidradar.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {
	// initialize database which only have one instance
	private val database = AppDatabase.getDatabase()
	val aster = database.asterDao()
	
	suspend fun getFeeds() = withContext(Dispatchers.IO) {
		AsteriodAPIClient.asteriodApi.getNeoFeed(
			startDate = getCurrentDate(),
			endDate = getFutureDate()
		)
	}
	
	suspend fun getTodayNasaImage() = withContext(Dispatchers.IO) {
		AsteriodAPIClient.asteriodApi.getTodayImage()
	}
	
	// we can't run db queries on main UI thread so a different thread
	@WorkerThread
	suspend fun insertAllAsteroids(asteroid: List<Asteroid>) = aster.insertAsterData(asteroid)
	
	@WorkerThread
	suspend fun getAllAsteroids() = aster.getAsterDataFromToday(getCurrentDate())
}