package com.udacity.asteroidradar.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidApp

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
	abstract fun asterDao(): AsterDao
	companion object {
		// Singleton prevents multiple instances of database opening at the
		// same time.
		@Volatile
		private var INSTANCE: AppDatabase? = null
		
		fun getDatabase(): AppDatabase {
			// if the INSTANCE is not null, then return it,
			// if it is, then create the database
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					AsteroidApp.getAppInstance(),
					AppDatabase::class.java,
					"asteroid.db"
				).build()
				INSTANCE = instance
				// return instance
				instance
			}
		}
	}
}