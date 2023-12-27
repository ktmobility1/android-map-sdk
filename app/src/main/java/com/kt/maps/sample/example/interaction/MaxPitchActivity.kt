package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.OnCameraChangeListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMaxPitchBinding

class MaxPitchActivity : BaseActivity<ActivityMaxPitchBinding>(R.layout.activity_max_pitch),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MaxPitchActivity)
            //set max pitch
            mapOptions.maxPitch(50f)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            // 지도 카메라 변경에 대한 리스너를 등록한다.
            addOnCameraChangedListener(
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