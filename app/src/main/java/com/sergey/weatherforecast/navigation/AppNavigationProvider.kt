package com.sergey.weatherforecast.navigation

import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.navigation.navigate
import com.sergey.weatherforecast.ui.destinations.DetailsScreenDestination
import com.sergey.weatherforecast.ui.destinations.ForecastScreenDestination

class AppNavigationProvider(private val navController: NavHostController) : NavigationProvider {

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun openForecastScreen(locationId: Long) {
        navController.navigate(direction = ForecastScreenDestination(locationId))
    }

    override fun openDetailsScreen(locationId: Long, day: Long) {
        navController.navigate(direction = DetailsScreenDestination(locationId, day))
    }

}
