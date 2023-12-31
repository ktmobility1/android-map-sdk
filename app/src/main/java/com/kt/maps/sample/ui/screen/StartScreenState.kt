package com.kt.maps.sample.ui.screen

sealed class StartScreenState {

    object Loading : StartScreenState()

    data class Data(val examples: List<Example>) : StartScreenState()

    data class Error(val message: String) : StartScreenState()
}