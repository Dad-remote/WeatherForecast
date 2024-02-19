package com.sergey.weatherforecast.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.sergey.weatherforecast.R
import com.sergey.weatherforecast.domain.model.WeatherIcons
import com.sergey.weatherforecast.navigation.NavigationProvider
import com.sergey.weatherforecast.ui.widgets.WeatherActionBar
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.round

private const val FORECAST_DAY_TEMPLATE = "HH:mm:ss"
private val dateFormatter = SimpleDateFormat(FORECAST_DAY_TEMPLATE, Locale.ENGLISH)

data class DetailsScreenNavArgs(val locationId: Long, val day: Long)

@Destination(navArgsDelegate = DetailsScreenNavArgs::class)
@Composable
fun DetailsScreen(
    navigator: NavigationProvider,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    DetailsContent(
        state = state,
        handleEvent = viewModel::onNewEvent
    )

    val effect by viewModel.effect.collectAsState(initial = null)
    DetailsEffect(
        effect = effect,
        clearEffect = viewModel::clearEffect,
        navigator = navigator,
    )
}

@Composable
private fun DetailsContent(
    state: DetailsState,
    handleEvent: (DetailsEvent) -> Unit,
) {

    Box(contentAlignment = Alignment.BottomEnd) {
        Column(
            modifier = Modifier
        ) {
            WeatherActionBar(
                titleResId = R.string.details,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onBack = { handleEvent(DetailsEvent.OnBackClicked) }
            )

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = WeatherIcons.find(state.details?.icon)),
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        DetailsItem(
                            titleResId = R.string.temperature_day,
                            value = state.details?.temperatureDay?.let { round(it) }.toString()
                        )

                        DetailsItem(
                            titleResId = R.string.temperature_feels_like,
                            value = state.details?.temperatureDay?.let { round(it) }.toString()
                        )
                    }
                }

                Spacer(modifier = Modifier.size(8.dp))

                DetailsItem(
                    titleResId = R.string.temperature_night,
                    value = state.details?.temperatureNight?.let { round(it) }.toString()
                )

                DetailsItem(
                    titleResId = R.string.temperature_feels_like,
                    value = state.details?.temperatureNight?.let { round(it) }.toString()
                )

                Spacer(modifier = Modifier.size(8.dp))

                DetailsItem(
                    titleResId = R.string.humidity,
                    value = state.details?.humidity.toString()
                )

                Spacer(modifier = Modifier.size(8.dp))

                DetailsItem(
                    titleResId = R.string.pressure,
                    value = state.details?.pressure.toString()
                )

                Spacer(modifier = Modifier.size(8.dp))

                DetailsItem(
                    titleResId = R.string.sunrise_at,
                    value = state.details?.let { dateFormatter.format(state.details.sunrise) }?.toString()
                )

                Spacer(modifier = Modifier.size(8.dp))

                DetailsItem(
                    titleResId = R.string.sunset_at,
                    value = state.details?.let { dateFormatter.format(state.details.sunset) }?.toString()
                )
            }
        }
    }

}

@Composable
private fun DetailsEffect(
    effect: DetailsEffect?,
    clearEffect: () -> Unit,
    navigator: NavigationProvider,
) {
    when (effect) {
        DetailsEffect.GoBack -> navigator.navigateUp()
        null -> Unit
    }
    clearEffect()
}
