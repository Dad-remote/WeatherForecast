package com.sergey.weatherforecast.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.sergey.weatherforecast.navigation.AppNavigationProvider
import com.sergey.weatherforecast.navigation.ApplicationNavGraph
import com.sergey.weatherforecast.navigation.NavigationProvider
import com.sergey.weatherforecast.ui.theme.WeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val navigationProvider = AppNavigationProvider(navController)

            WeatherForecastTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DestinationsNavHost(
                        navController = navController,
                        navGraph = ApplicationNavGraph,
                        dependenciesContainerBuilder = {
                            dependency(navigationProvider as NavigationProvider)
                        }
                    )
                }
            }
        }
    }
}
