package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Utils.getCurrentDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {
	// initialize repository there
	private val repo = MainRepository()
	
	// Mutable live data is private so it could no update from view side
	// viewModel will be responsible to update the data to UI
	private val _feeds = MutableLiveData<List<Asteroid>>()
	val feeds: LiveData<List<Asteroid>> = _feeds
	
	private val _mainImage = MutableLiveData<PictureOfDay>()
	val mainImage: LiveData<PictureOfDay> = _mainImage
	
	fun getFeeds() {
		// viewModel scope tied with UI
		// coroutine will be work in other background thread as you can see we are using IO method
		viewModelScope.launch(Dispatchers.IO) {
			
			// check first if data exist then get data from it and return
			val asterList = repo.getAllAsteroids()
			if (asterList.isNotEmpty()){
				_feeds.postValue(asterList)
				Log.d("database", "true")
				return@launch
			}
			
			// if database is empty and have no record then fetch via api for the very first time
			// for subsequent request in background work manager will be used to fetch the data
			
			// try catch block is for to handle the exception as we are not using any wrapper
			// to handle the response nature otherwise no need for try catch block
			try {
				val response = repo.getFeeds()
				if (response.body() != null) {
					val json = JSONObject(response.body().toString())
					val parseResponse = parseAsteroidsJsonResult(json)
					repo.insertAllAsteroids(parseResponse)
					_feeds.postValue(parseResponse)
					Log.d("response", json.toString())
				}
			}
			catch (e: Exception) {
				Log.d("response", e.toString())
			}
		}
	}
	
	// this api would only call when internet will be available
	// not saving it into database
	fun getTopImage() {
		viewModelScope.launch(Dispatchers.IO) {
			try {
				val response = repo.getTodayNasaImage()
				if (response.body() != null) {
					_mainImage.postValue(response.body())
				}
			} catch (ex: Exception) {
				Log.d("exception", ex.toString())
			}
		}
	}
}