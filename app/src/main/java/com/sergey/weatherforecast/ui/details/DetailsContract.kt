package com.sergey.weatherforecast.ui.details

import com.sergey.weatherforecast.domain.model.ForecastItem
import com.sergey.weatherforecast.ui.base.UiEffect
import com.sergey.weatherforecast.ui.base.UiEvent
import com.sergey.weatherforecast.ui.base.UiState
import com.sergey.weatherforecast.ui.base.VMState
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailsState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val details: ForecastItem? = null,
) : UiState

@Parcelize
data class InnerDetailsState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
) : VMState

sealed class DetailsEvent : UiEvent {
    object OnBackClicked : DetailsEvent()
}

sealed class DetailsEffect : UiEffect {
    object GoBack : DetailsEffect()
}
