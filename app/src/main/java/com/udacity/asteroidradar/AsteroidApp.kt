package com.udacity.asteroidradar

import android.app.Application

class AsteroidApp : Application() {
	
	override fun onCreate() {
		super.onCreate()
		appInstance = this
	}
	
	companion object {
		private lateinit var appInstance: Application
		fun getAppInstance() = appInstance.applicationContext
	}
}