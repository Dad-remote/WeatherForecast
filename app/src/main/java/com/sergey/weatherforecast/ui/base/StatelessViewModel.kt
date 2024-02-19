package com.sergey.weatherforecast.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class StatelessViewModel<EFFECT : UiEffect, EVENT : UiEvent> : ViewModel() {

    private val _effect: Channel<EFFECT?> = Channel()

    val effect = _effect.receiveAsFlow()

    protected abstract fun handleEvent(event: EVENT)

    fun onNewEvent(event: EVENT) = handleEvent(event)

    protected fun setEffect(effect: EFFECT) {
        viewModelScope.launch {
            _effect.send(null)
            _effect.send(effect)
        }
    }

    fun clearEffect() {
        viewModelScope.launch { _effect.send(null) }
    }
}
