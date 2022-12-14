package com.udacity.asteroidradar.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Utils.getCurrentDate
import com.udacity.asteroidradar.Utils.getFutureDate
import com.udacity.asteroidradar.api.AsteriodAPIClient
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

// for android > 12 need to use work manager with foreground service
class SyncWorker(context: Context, workerParameters: WorkerParameters) :
	CoroutineWorker(context, workerParameters) {
	
	val asterDao = AppDatabase.getDatabase().asterDao()
	
	override suspend fun doWork(): Result {
		val response = AsteriodAPIClient.asteriodApi.getNeoFeed(
			startDate = getCurrentDate(), endDate = getFutureDate()
		)
		response.body()?.let {
			withContext(Dispatchers.IO) {
				try {
					if (response.body() != null) {
						// delete data if exist
						asterDao.deleteAsterRecord()
						// save data into database
						// filter data through NetworkUtils because we are getting response in string form
						val json = JSONObject(response.body().toString())
						val parseResponse = parseAsteroidsJsonResult(json)
						asterDao.deleteAsterRecord()
						asterDao.insertAsterData(parseResponse)
						Log.d("worker", json.toString())
						return@withContext Result.success()
					} else {
						Log.d("worker", "fail")
						return@withContext Result.failure()
					}
				} catch (e: Exception) {
					Log.d("worker", e.toString())
					return@withContext Result.failure()
				}
			}
			
		}
		
		if (!response.isSuccessful && response.code() > 400) {
			Log.d("worker", "retry")
			return Result.retry()
		}
		
		return Result.success()
	}
}