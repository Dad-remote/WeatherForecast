package com.sergey.weatherforecast.ui.locations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
class LocationsViewModel @Inject constructor(
    savedState: SavedStateHandle,
    private val locationsRepository: LocationsRepository
) : FlowViewModel<InnerLocationsState, LocationsState, LocationsEffect, LocationsEvent>(savedState, InnerLocationsState()) {

    override val viewState: StateFlow<LocationsState> = combine(state, locationsRepository.loadLocations()) { innerState, locations ->
        LocationsState(
            isLoading = innerState.isLoading,
            showNewLocationDialog = innerState.showNewLocationDialog,
            showError = innerState.showError,
            locations = locations
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, LocationsState())

    override fun handleEvent(event: LocationsEvent) {
        when (event) {
            LocationsEvent.OnBackClicked -> {}
            LocationsEvent.OnAddClicked -> updateState { copy(showNewLocationDialog = true) }
            LocationsEvent.OnDialogDismissed -> updateState { copy(showNewLocationDialog = false) }
            LocationsEvent.OnErrorDialogDismissed -> updateState { copy(showError = false) }
            is LocationsEvent.OnNewLocation -> saveLocation(event.name, event.latitude, event.longitude)
            is LocationsEvent.OnLocationClicked -> setEffect(LocationsEffect.ShowLocation(event.location))
        }
    }

    private fun saveLocation(name: String, latitude: String, longitude: String) {
        try {
            val lat = latitude.toFloat()
            val lng = longitude.toFloat()
            viewModelScope.launch {
                val result = locationsRepository.saveLocation(name, lat, lng)
                when {
                    result.isFailure -> updateState { copy(showError = true) }
                    else -> updateState { copy(showNewLocationDialog = false) }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
