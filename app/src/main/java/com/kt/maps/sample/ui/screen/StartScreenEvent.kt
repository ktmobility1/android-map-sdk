package com.kt.maps.sample.ui.screen

sealed class StartScreenEvent {
    data class TapExample(val example: Example) : StartScreenEvent()

    object PermissionChecked : StartScreenEvent()
}