package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object AsteriodAPIClient {
	private val moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()
	
	private val retrofit = Retrofit.Builder()
		.baseUrl(BASE_URL)
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.build()
	
	val asteriodApi by lazy { retrofit.create(ApiRoute::class.java) }
}