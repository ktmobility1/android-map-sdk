package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.gesture.gestureSettings
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityDoubleClickZoomHandlerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback


class DoubleClickZoomHandlerActivity :
    BaseActivity<ActivityDoubleClickZoomHandlerBinding>(R.layout.activity_double_click_zoom_handler),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        binding.zoomToggleButton.isChecked = true
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        map.currentLocation.enabled = false
        map.zoomControls.enabled = false
        map.panControls.enabled = false

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