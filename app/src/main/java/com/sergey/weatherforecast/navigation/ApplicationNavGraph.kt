package com.sergey.weatherforecast.navigation

import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.sergey.weatherforecast.ui.AppNavGraph

object ApplicationNavGraph : NavGraphSpec {
    override val route = "root"

    override val destinationsByRoute = emptyMap<String, DestinationSpec<*>>()

    override val startRoute = AppNavGraph

    override val nestedNavGraphs = listOf(
        AppNavGraph
    )
}
