package com.kt.maps.sample.ui.screen

sealed class StartScreenEvent {
    data class ClickExample(val example: Example): StartScreenEvent()

    object PermissionChecked: StartScreenEvent()
}