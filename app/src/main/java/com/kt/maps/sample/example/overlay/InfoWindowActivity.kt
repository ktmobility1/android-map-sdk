package com.kt.maps.sample.example.overlay

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityInfowindowBinding

class InfoWindowActivity : BaseActivity<ActivityInfowindowBinding>(R.layout.activity_infowindow),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@InfoWindowActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 15f,
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103)
                )
            )

            setAllowConcurrentMultipleOpenInfoWindows(true)

            val marker1 = addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER1_POSITION)
                    title("Marker 1")
                    snippet("${MARKER1_POSITION.longitude}, ${MARKER1_POSITION.latitude} ")
                }.build()
            )
            selectMarker(marker1)

            val marker2 = addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER2_POSITION)
                    title("Marker 2")
                    snippet("${MARKER2_POSITION.longitude}, ${MARKER2_POSITION.latitude} ")
                }.build()
            )
            marker2.isSelected = true

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

    companion object {
        private val MARKER1_POSITION = LngLat(longitude = 126.97794, latitude = 37.57103)
        private val MARKER2_POSITION = LngLat(longitude = 126.97620, latitude = 37.56945)
    }
}