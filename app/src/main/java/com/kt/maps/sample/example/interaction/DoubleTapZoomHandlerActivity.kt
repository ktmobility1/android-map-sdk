package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.gesture.gestureSettings
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityDoubleTapZoomHandlerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback


class DoubleTapZoomHandlerActivity :
    BaseActivity<ActivityDoubleTapZoomHandlerBinding>(R.layout.activity_double_tap_zoom_handler),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@DoubleTapZoomHandlerActivity)
        }
        binding.zoomToggleButton.isChecked = true
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            currentLocation.enabled = false
            zoomControls.enabled = false
            panControls.enabled = false
        }

        // switch button 상태에 따라 gesture settings 설정
        binding.zoomToggleButton.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.doubleTapEnabled = isChecked
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

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}