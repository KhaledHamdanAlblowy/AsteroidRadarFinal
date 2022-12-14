package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRoute {
	@GET("neo/rest/v1/feed")
	suspend fun getNeoFeed(
		@Query("api_key") apiKey: String = API_KEY,
		@Query("start_date") startDate: String,
		@Query("end_date") endDate: String
	): Response<String>
	
	@GET("planetary/apod")
	suspend fun getTodayImage(
		@Query("api_key") apiKey: String = API_KEY
	): Response<PictureOfDay>
}