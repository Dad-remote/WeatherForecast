package com.sergey.weatherforecast.ui.locations

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.sergey.weatherforecast.R
import com.sergey.weatherforecast.ui.widgets.WeatherActionBar
import androidx.hilt.navigation.compose.hiltViewModel
import com.sergey.weatherforecast.navigation.NavigationProvider
import com.sergey.weatherforecast.ui.widgets.GeneralErrorDialog

@Destination(start = true)
@Composable
fun LocationsScreen(
    navigator: NavigationProvider,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    LocationsContent(
        state = state,
        handleEvent = viewModel::onNewEvent
    )

    val effect by viewModel.effect.collectAsState(initial = null)
    LocationsEffect(
        effect = effect,
        clearEffect = viewModel::clearEffect,
        navigator = navigator,
    )

    BackPressHandler(onBackPressed = { viewModel.onNewEvent(LocationsEvent.OnBackClicked) })
}

@Composable
private fun LocationsContent(
    state: LocationsState,
    handleEvent: (LocationsEvent) -> Unit,
) {

    Box(contentAlignment = Alignment.BottomEnd) {

        Column(modifier = Modifier.fillMaxSize()) {

            WeatherActionBar(titleResId = R.string.locations)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.locations) { location ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { handleEvent(LocationsEvent.OnLocationClicked(location)) }
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = location.name,
                            fontSize = 24.sp
                        )

                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = "[${location.lat}, ${location.lng}]"
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier.padding(end = 16.dp, bottom = 24.dp),
            onClick = { handleEvent(LocationsEvent.OnAddClicked) },
        ) {
            Icon(Icons.Filled.Add, null)
        }

        if (state.showNewLocationDialog) {
            NewLocationDialog(
                onDismissRequest = { handleEvent(LocationsEvent.OnDialogDismissed) },
                onConfirmation = { name, latitude, longitude -> handleEvent(LocationsEvent.OnNewLocation(name, latitude, longitude)) }
            )
        }

        if (state.showError) {
            GeneralErrorDialog(onDismissRequest = { handleEvent(LocationsEvent.OnErrorDialogDismissed) })
        }
    }
}

@Composable
private fun LocationsEffect(
    effect: LocationsEffect?,
    clearEffect: () -> Unit,
    navigator: NavigationProvider,
) {
    when (effect) {
        LocationsEffect.GoBack -> navigator.navigateUp()
        is LocationsEffect.ShowLocation -> navigator.openForecastScreen(effect.location.id)
        null -> Unit
    }
    clearEffect()
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}
