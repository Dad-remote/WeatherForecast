package com.sergey.weatherforecast.ui.locations

import com.sergey.weatherforecast.domain.model.WeatherLocation
import com.sergey.weatherforecast.ui.base.UiEffect
import com.sergey.weatherforecast.ui.base.UiEvent
import com.sergey.weatherforecast.ui.base.UiState
import com.sergey.weatherforecast.ui.base.VMState
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationsState(
    val isLoading: Boolean = false,
    val showNewLocationDialog: Boolean = false,
    val showError: Boolean = false,
    val locations: List<WeatherLocation> = emptyList(),
) : UiState

@Parcelize
data class InnerLocationsState(
    val isLoading: Boolean = false,
    val showNewLocationDialog: Boolean = false,
    val showError: Boolean = false,
) : VMState

sealed class LocationsEvent : UiEvent {
    object OnBackClicked : LocationsEvent()
    object OnAddClicked : LocationsEvent()
    object OnDialogDismissed : LocationsEvent()
    object OnErrorDialogDismissed : LocationsEvent()
    data class OnNewLocation(val name: String, val latitude: String, val longitude: String) : LocationsEvent()
    data class OnLocationClicked(val location: WeatherLocation) : LocationsEvent()
}

sealed class LocationsEffect : UiEffect {
    object GoBack : LocationsEffect()
    data class ShowLocation(val location: WeatherLocation) : LocationsEffect()
}
