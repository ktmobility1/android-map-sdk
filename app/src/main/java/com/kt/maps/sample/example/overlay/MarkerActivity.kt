package com.kt.maps.sample.example.overlay

import android.graphics.BitmapFactory
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMarkerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class MarkerActivity : BaseActivity<ActivityMarkerBinding>(R.layout.activity_marker),
    OnMapReadyCallback {

    private lateinit var map: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            this.jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 15.0,
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103)
                )
            )

            addOverlay(
                MarkerOptions.Builder().apply {
                    position(MARKER1_POSITION)
                    title("Marker 1")
                    snippet("${MARKER1_POSITION.longitude}, ${MARKER1_POSITION.latitude} ")
                }.build()
            )

            addOverlay(

                MarkerOptions.Builder().apply {
                    position(MARKER2_POSITION)
                    icon(BitmapFactory.decodeResource(resources, R.drawable.ic_map_marker_01))
                    title("Marker 2")
                    snippet("${MARKER2_POSITION.longitude}, ${MARKER2_POSITION.latitude} ")
                }.build()
            )


            val markerList = listOf(
                MarkerOptions.Builder().apply {
                    position(MARKER3_POSITION)
                    icon(BitmapFactory.decodeResource(resources, R.drawable.ic_map_marker_02))
                    title("Marker 3")
                    snippet("${MARKER3_POSITION.longitude}, ${MARKER3_POSITION.latitude} ")
                }.build(),
                MarkerOptions.Builder().apply {
                    position(MARKER4_POSITION)
                    title("Marker 4")
                    snippet("${MARKER4_POSITION.longitude}, ${MARKER4_POSITION.latitude} ")
                }.build()
            )

            addOverlays(markerList)

        }

    }

    companion object {
        private val MARKER1_POSITION = LngLat(longitude = 126.97794, latitude = 37.57103)
        private val MARKER2_POSITION = LngLat(longitude = 126.97620, latitude = 37.56945)
        private val MARKER3_POSITION = LngLat(longitude = 126.97892, latitude = 37.57351)
        private val MARKER4_POSITION = LngLat(longitude = 126.97511, latitude = 37.57354)
    }
}