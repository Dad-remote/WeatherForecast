package com.sergey.weatherforecast.ui.base

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class FlowViewModel<VMSTATE : VMState, STATE : UiState, EFFECT : UiEffect, EVENT : UiEvent>(
    private val savedStateHandle: SavedStateHandle,
    initialState: VMSTATE
) : StatelessViewModel<EFFECT, EVENT>() {

    companion object {
        private const val STATE_SAVE_KEY = "STATE_SAVE_KEY"
    }

    abstract val viewState: StateFlow<STATE>

    private val _state = MutableStateFlow(
        savedStateHandle.get<VMSTATE>(STATE_SAVE_KEY) ?: initialState
    )

    protected val state: StateFlow<VMSTATE>
        get() = _state.asStateFlow()

    protected fun updateState(update: (VMSTATE.() -> VMSTATE)) {
        val actualValue = _state.value
        val newState = update(actualValue)
        savedStateHandle.set(STATE_SAVE_KEY, newState)
        _state.value = newState
    }
}
