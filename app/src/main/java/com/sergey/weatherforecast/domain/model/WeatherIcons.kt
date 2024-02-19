package com.sergey.weatherforecast.domain.model

import com.sergey.weatherforecast.R

enum class WeatherIcons(private val code: String, private val iconResId: Int) {
    DEFAULT("", R.drawable.ic_thermostat),
    SUNNY("01d", R.drawable.ic_wb_sunny_24),
    CLOUDS("04d", R.drawable.ic_cloud_queue_24),
    CLOUDS2("03d", R.drawable.ic_cloud_queue_24);

    companion object {
        fun find(iconCode: String?): Int = entries.find { it.code == iconCode }?.iconResId ?: DEFAULT.iconResId
    }
}
