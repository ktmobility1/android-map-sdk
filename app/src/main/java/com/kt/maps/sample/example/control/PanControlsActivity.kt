package com.kt.maps.sample.example.control

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.control.compass.compass
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.logo.logo
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.scale.scaleBar
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPanControlsBinding

class PanControlsActivity :
    BaseActivity<ActivityPanControlsBinding>(R.layout.activity_pan_controls), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@PanControlsActivity)
        }
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

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}