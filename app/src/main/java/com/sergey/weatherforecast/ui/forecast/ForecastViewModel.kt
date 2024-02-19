package com.sergey.weatherforecast.ui.forecast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sergey.weatherforecast.domain.repository.ForecastRepository
import com.sergey.weatherforecast.domain.repository.LocationsRepository
import com.sergey.weatherforecast.ui.base.FlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val forecastRepository: ForecastRepository
) : FlowViewModel<InnerForecastState, ForecastState, ForecastEffect, ForecastEvent>(savedState, InnerForecastState()) {

    val locationId = savedState.get<Long>("locationId") ?: throw IllegalArgumentException("location id is null")

    override val viewState: StateFlow<ForecastState> = combine(state, forecastRepository.loadForecastForLocation(locationId)) { innerState, forecasts ->
        ForecastState(
            isLoading = innerState.isLoading,
            showError = innerState.showError,
            forecasts = forecasts
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, ForecastState())

    init {
        viewModelScope.launch {
            forecastRepository.syncForecastForLocation(locationId)
        }
    }
    override fun handleEvent(event: ForecastEvent) {
        when (event) {
            ForecastEvent.OnBackClicked -> setEffect(ForecastEffect.GoBack)
            ForecastEvent.OnErrorDialogDismissed -> updateState { copy(showError = false) }
            is ForecastEvent.OnDayClicked -> setEffect(ForecastEffect.OpenDay(event.forecastItem))
        }
    }


}
