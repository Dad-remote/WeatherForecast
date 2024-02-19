package com.sergey.weatherforecast.ui.forecast

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.sergey.weatherforecast.R
import com.sergey.weatherforecast.ui.widgets.WeatherActionBar
import androidx.hilt.navigation.compose.hiltViewModel
import com.sergey.weatherforecast.domain.model.WeatherIcons
import com.sergey.weatherforecast.navigation.NavigationProvider
import com.sergey.weatherforecast.ui.widgets.GeneralErrorDialog
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.round

private val FORECAST_DAY_TEMPLATE = "dd/MM, yyyy"
private val dateFormatter = SimpleDateFormat(FORECAST_DAY_TEMPLATE, Locale.ENGLISH)

data class ForecastScreenNavArgs(val locationId: Long)

@Destination(navArgsDelegate = ForecastScreenNavArgs::class)
@Composable
fun ForecastScreen(
    navigator: NavigationProvider,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    ForecastContent(
        state = state,
        handleEvent = viewModel::onNewEvent
    )

    val effect by viewModel.effect.collectAsState(initial = null)
    ForecastEffect(
        effect = effect,
        clearEffect = viewModel::clearEffect,
        navigator = navigator,
    )
}

@Composable
private fun ForecastContent(
    state: ForecastState,
    handleEvent: (ForecastEvent) -> Unit,
) {

    Box(contentAlignment = Alignment.BottomEnd) {

        Column(modifier = Modifier.fillMaxSize()) {

            WeatherActionBar(
                titleResId = R.string.forecast,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onBack = { handleEvent(ForecastEvent.OnBackClicked) }
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {

                items(state.forecasts) { forecast ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { handleEvent(ForecastEvent.OnDayClicked(forecast)) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(16.dp),
                            painter = painterResource(id = WeatherIcons.find(forecast.icon)),
                            contentDescription = null
                        )

                        Column {
                            Text(text = dateFormatter.format(forecast.date), fontSize = 24.sp)

                            Text(text = stringResource(id = R.string.forecast_item_temperature, round(forecast.temperatureDay)))
                        }
                    }
                }
            }
        }

        if (state.showError) {
            GeneralErrorDialog(onDismissRequest = { handleEvent(ForecastEvent.OnErrorDialogDismissed) })
        }
    }

}

@Composable
private fun ForecastEffect(
    effect: ForecastEffect?,
    clearEffect: () -> Unit,
    navigator: NavigationProvider,
) {
    when (effect) {
        ForecastEffect.GoBack -> navigator.navigateUp()
        is ForecastEffect.OpenDay -> navigator.openDetailsScreen(effect.forecastItem.locationId, effect.forecastItem.date)
        null -> Unit
    }
    clearEffect()
}

