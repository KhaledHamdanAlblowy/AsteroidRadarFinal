package com.udacity.asteroidradar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsterDao {
	// insert
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insertAsterData(asteroid: List<Asteroid>)

	// fetch data
	@Query("SELECT * FROM Asteroid WHERE closeApproachDate >= :date ORDER BY closeApproachDate DESC")
	fun getAsterDataFromToday(date: String): List<Asteroid>

	// week asteroids
	@Query("SELECT * FROM Asteroid ORDER BY closeApproachDate DESC")
	fun getWeekAsteroids(): List<Asteroid>

	// today asteroids
	@Query("SELECT * FrOM Asteroid WHERE closeApproachDate = :date ORDER BY closeApproachDate DESC")
	fun getCurrentAsteroids(date: String): List<Asteroid>

	// fetch selected aster
	@Query("SELECT * FROM Asteroid WHERE id = :asterId")
	fun getSelectedAster(asterId: Long): Asteroid

	// delete
	@Query("DELETE FROM Asteroid")
	fun deleteAsterRecord()
}