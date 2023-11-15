package com.kt.maps.sample.example.overlay

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.polygon.PolygonOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolygonOverlayBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class PolygonOverlayActivity :
    BaseActivity<ActivityPolygonOverlayBinding>(R.layout.activity_polygon_overlay),
    OnMapReadyCallback {

    private lateinit var map: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.map.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 15.0,
                    lngLat = LngLat(longitude = 126.97794, latitude = 37.57103)
                )
            )

            addOverlay(
                PolygonOverlayOptions.Builder().apply {
                    coords(POLYGON_OVERLAY_1_COORDS)
                    color(Color.GREEN)
                    holes(POLYGON_OVERLAY_1_HOLES)
                }.build()
            )

            addOverlay(
                PolygonOverlayOptions.Builder().apply {
                    coords(POLYGON_OVERLAY_2_COORDS)
                    color(Color.YELLOW)
                    outlineColor(Color.RED)
                    opacity(0.5f)
                }.build()
            )

        }

    }

    companion object {
        private val POLYGON_OVERLAY_1_COORDS = listOf(
            LngLat(latitude = 37.57427, longitude = 126.97796),
            LngLat(latitude = 37.57256, longitude = 126.97513),
            LngLat(latitude = 37.57067, longitude = 126.97664),
            LngLat(latitude = 37.57079, longitude = 126.97924),
            LngLat(latitude = 37.57264, longitude = 126.97958)
        )
        private val POLYGON_OVERLAY_1_HOLES = listOf(
            listOf(
                LngLat(latitude = 37.57316, longitude = 126.97783),
                LngLat(latitude = 37.57171, longitude = 126.97695),
                LngLat(latitude = 37.57159, longitude = 126.97855)
            )
        )

        private val POLYGON_OVERLAY_2_COORDS = listOf(
            LngLat(latitude = 37.56945, longitude = 126.97815),
            LngLat(latitude = 37.56974, longitude = 126.98025),
            LngLat(latitude = 37.56863, longitude = 126.98005),
            LngLat(latitude = 37.56842, longitude = 126.97795)
        )
    }

}