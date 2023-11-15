package com.kt.maps.sample.example.camera

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityGestureCameraBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class GestureCameraActivity :
    BaseActivity<ActivityGestureCameraBinding>(R.layout.activity_gesture_camera),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.cameraMap
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        binding.zoomSlider.addOnChangeListener { _, value, _ ->
            binding.zoomSliderValue.text = value.toString()

            map.easeTo(
                CameraPositionOptions(zoom = value.toDouble()),
                duration = 70
            )
        }

        binding.bearingSlider.addOnChangeListener { _, value, _ ->
            binding.bearingSliderValue.text = value.toString()

            map.easeTo(
                CameraPositionOptions(bearing = value.toDouble()),
                duration = 70
            )
        }

        binding.pitchSlider.addOnChangeListener { _, value, _ ->
            binding.pitchSliderValue.text = value.toString()

            map.easeTo(
                CameraPositionOptions(pitch = value.toDouble()),
                duration = 70
            )
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