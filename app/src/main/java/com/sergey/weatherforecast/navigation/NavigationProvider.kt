package com.sergey.weatherforecast.navigation

interface NavigationProvider {

    fun navigateUp()
    fun openForecastScreen(locationId: Long)
    fun openDetailsScreen(locationId: Long, day: Long)

}
