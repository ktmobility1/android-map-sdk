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
        map = ktmap.apply {
            //only pancontrols enable
            compass.enabled = false
            zoomControls.enabled = false
            logo.enabled = false
            scaleBar.enabled = false
            currentLocation.enabled = false
            panControls.enabled = true
        }
    }
}