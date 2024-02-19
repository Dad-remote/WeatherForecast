package com.sergey.weatherforecast.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sergey.weatherforecast.domain.repository.ForecastRepository
import com.sergey.weatherforecast.ui.base.FlowViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val forecastRepository: ForecastRepository
) : FlowViewModel<InnerDetailsState, DetailsState, DetailsEffect, DetailsEvent>(savedState, InnerDetailsState()) {

    val locationId = savedState.get<Long>("locationId") ?: throw IllegalArgumentException("location id is null")
    val day = savedState.get<Long>("day") ?: throw IllegalArgumentException("day id is null")

    override val viewState: StateFlow<DetailsState> = combine(state, forecastRepository.loadDetails(locationId, day)) { innerState, forecasts ->
        DetailsState(
            isLoading = innerState.isLoading,
            showError = innerState.showError,
            details = forecasts
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DetailsState())

    override fun handleEvent(event: DetailsEvent) {
        when (event) {
            DetailsEvent.OnBackClicked -> setEffect(DetailsEffect.GoBack)
        }
    }


}
