package com.kt.maps.sample.example.interaction

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.control.compass.compass
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMapExtentBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class MapExtentActivity : BaseActivity<ActivityMapExtentBinding>(R.layout.activity_map_extent),
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

        map.zoomControls.enabled = false
        map.compass.enabled = false

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 6.0,
                    lngLat = LngLat(longitude = 127.773138, latitude = 36.61819)
                )
            )

            addOverlay(
                PolylineOverlayOptions.Builder().apply {
                    lngLats(MAP_EXTENT_POLYLINE)
                    color(Color.BLACK)
                    width(6f)
                }.build()
            )
        }
    }

    companion object {
        val MAP_EXTENT_POLYLINE = listOf(
            LngLat(latitude = 39.38017, longitude = 125.85205),
            LngLat(latitude = 33.98230, longitude = 125.85205),
            LngLat(latitude = 33.98230, longitude = 129.64380),
            LngLat(latitude = 39.38017, longitude = 129.64380),
            LngLat(latitude = 39.38017, longitude = 125.85205),
            LngLat(latitude = 33.98230, longitude = 125.85205),
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