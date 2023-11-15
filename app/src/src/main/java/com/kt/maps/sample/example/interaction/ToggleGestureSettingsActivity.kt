package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.gesture.gestureSettings
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityToggleGestureSettingsBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class ToggleGestureSettingsActivity :
    BaseActivity<ActivityToggleGestureSettingsBinding>(R.layout.activity_toggle_gesture_settings),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap
        map.currentLocation.enabled = false
        map.zoomControls.enabled = false

        binding.zoomToggle.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.zoomEnabled = isChecked
        }

        binding.pitchToggle.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.pitchEnabled = isChecked
        }

        binding.rotateToggle.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.rotateEnabled = isChecked
        }

        binding.scrollToggle.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.scrollEnabled = isChecked
        }

        binding.horizontalScrollToggle.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.horizontalScrollEnabled = isChecked
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