package com.kt.maps.sample.example.control

import android.os.Bundle
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPanControlsBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class PanControlsActivity :
    BaseActivity<ActivityPanControlsBinding>(R.layout.activity_pan_controls), OnMapReadyCallback {

    private lateinit var map: KtMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {

        map = ktmap

        //only pancontrols enable
        map.compass.enabled = false
        map.zoomControls.enabled = false
        map.logo.enabled = false
        map.scaleBar.enabled = false
        map.currentLocation.enabled = false
        map.panControls.enabled = true
    }
}