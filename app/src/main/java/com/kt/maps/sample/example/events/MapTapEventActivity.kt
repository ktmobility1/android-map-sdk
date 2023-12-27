package com.kt.maps.sample.example.events

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.gesture.addOnMapLongPressListener
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMapTapEventBinding
import com.kt.maps.sample.ui.common.showSnackbar

class MapTapEventActivity :
    BaseActivity<ActivityMapTapEventBinding>(R.layout.activity_map_tap_event),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MapTapEventActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            // 지도 Tap 이벤트 처리 하기 위한 리스너 등록
            addOnMapTapListener { point, lngLat ->
                mapView.showSnackbar(
                    R.string.format_map_tap,
                    lngLat.longitude,
                    lngLat.latitude,
                    point.x,
                    point.y,
                )
                true
            }

            // 지도 LongPress 이벤트 처리 하기 위한 리스너 등록
            addOnMapLongPressListener { point, lngLat ->
                mapView.showSnackbar(
                    R.string.format_map_long_press,
                    lngLat.longitude,
                    lngLat.latitude,
                    point.x,
                    point.y
                )
                true
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}