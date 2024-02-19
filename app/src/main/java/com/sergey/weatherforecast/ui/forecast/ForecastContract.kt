package com.sergey.weatherforecast.ui.forecast

import com.sergey.weatherforecast.domain.model.ForecastItem
import com.sergey.weatherforecast.ui.base.UiEffect
import com.sergey.weatherforecast.ui.base.UiEvent
import com.sergey.weatherforecast.ui.base.UiState
import com.sergey.weatherforecast.ui.base.VMState
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForecastState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val forecasts: List<ForecastItem> = emptyList(),
) : UiState

@Parcelize
data class InnerForecastState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
) : VMState

sealed class ForecastEvent : UiEvent {
    object OnBackClicked : ForecastEvent()
    object OnErrorDialogDismissed : ForecastEvent()
    data class OnDayClicked(val forecastItem: ForecastItem) : ForecastEvent()
}

sealed class ForecastEffect : UiEffect {
    object GoBack : ForecastEffect()
    data class OpenDay(val forecastItem: ForecastItem) : ForecastEffect()
}
