package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.pan.panControls
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.gesture.gestureSettings
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityDoubleTapZoomHandlerBinding


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
            // Controls 사용 여부 설정
            currentLocation.enabled = false
            zoomControls.enabled = false
            panControls.enabled = false
        }

        // switch button 상태에 따라 제스쳐 설정
        binding.zoomToggleButton.setOnCheckedChangeListener { _, isChecked ->
            // Double Tap 제스쳐 활성화 설정: 활성화 되면 Double Tap 시 지도 확대 된다.
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}