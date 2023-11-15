package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.camera.OnCameraChangeListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMaxPitchBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class MaxPitchActivity : BaseActivity<ActivityMaxPitchBinding>(R.layout.activity_max_pitch),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        //set max pitch
        mapView.ktMapOptions.maxPitch(50.0)

    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.addOnCameraChangedListener(
            object : OnCameraChangeListener {
                override fun onCameraMoveStarted(reason: OnCameraChangeListener.REASON) {
                    binding.pitchLevelText.text =
                        getString(R.string.format_double, map.getCameraPosition().pitch)
                }

                override fun onCameraMoveCanceled() {
                    binding.pitchLevelText.text =
                        getString(R.string.format_double, map.getCameraPosition().pitch)
                }

                override fun onCameraMove() {
                    binding.pitchLevelText.text =
                        getString(R.string.format_double, map.getCameraPosition().pitch)
                }
            }
        )
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