package com.kt.maps.sample.example.events

import android.os.Bundle
import com.kt.maps.gesture.addOnMapLongTapListener
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMapClickEventBinding
import com.kt.maps.sample.ui.common.showToast
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class MapClickEventActivity :
    BaseActivity<ActivityMapClickEventBinding>(R.layout.activity_map_click_event),
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

        map.addOnMapTapListener() { point, coord ->
            baseContext.showToast(
                R.string.format_map_click,
                coord.latitude,
                coord.longitude
            )
            true
        }

        map.addOnMapLongTapListener() { point, coord ->
            baseContext.showToast(
                R.string.format_map_long_click,
                coord.latitude,
                coord.longitude
            )
            true
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