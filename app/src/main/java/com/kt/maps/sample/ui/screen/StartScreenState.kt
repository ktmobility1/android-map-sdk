package com.kt.maps.sample.ui.screen

import android.content.Intent

sealed class StartScreenState {

    object Loading : StartScreenState()

    data class Data(val examples: List<Example>) : StartScreenState()

    data class Error(val message: String) : StartScreenState()
}