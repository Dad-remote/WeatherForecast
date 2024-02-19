package com.sergey.weatherforecast.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
fun DetailsItem(titleResId: Int, value: String?) {
    value?.let {
        Column {
            Text(text = stringResource(id = titleResId))

            Text(text = value)
        }
    }
}
