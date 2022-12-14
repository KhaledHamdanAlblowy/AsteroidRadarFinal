package com.udacity.asteroidradar

import android.annotation.SuppressLint
import android.icu.util.Calendar
import com.udacity.asteroidradar.Constants.API_QUERY_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object Utils {
	@SuppressLint("SimpleDateFormat")
	fun getCurrentDate(): String {
		val date = Date()
		return SimpleDateFormat(API_QUERY_DATE_FORMAT).format(date)
	}
	
	@SuppressLint("NewApi")
	fun getFutureDate(): String {
		val calender = Calendar.getInstance()
		val date = SimpleDateFormat(API_QUERY_DATE_FORMAT)
		calender.add(Calendar.DAY_OF_YEAR, 7)
		return date.format(Date(calender.timeInMillis))
	}
}