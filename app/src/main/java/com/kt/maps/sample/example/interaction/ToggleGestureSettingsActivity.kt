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

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@ToggleGestureSettingsActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            currentLocation.enabled = false
            zoomControls.enabled = false
        }

        binding.run {
            zoomToggle.setOnCheckedChangeListener { _, isChecked ->
                ktmap.gestureSettings.zoomEnabled = isChecked
            }

            pitchToggle.setOnCheckedChangeListener { _, isChecked ->
                ktmap.gestureSettings.pitchEnabled = isChecked
            }

            rotateToggle.setOnCheckedChangeListener { _, isChecked ->
                ktmap.gestureSettings.rotateEnabled = isChecked
            }

            panToggle.setOnCheckedChangeListener { _, isChecked ->
                ktmap.gestureSettings.panEnabled = isChecked
            }

            horizontalPanToggle.setOnCheckedChangeListener { _, isChecked ->
                ktmap.gestureSettings.horizontalPanEnabled = isChecked
            }
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