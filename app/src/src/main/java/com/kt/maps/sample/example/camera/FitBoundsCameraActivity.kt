package com.kt.maps.sample.example.camera

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.camera.CameraBoundsOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.geometry.LngLatBounds
import com.kt.maps.overlay.polygon.PolygonOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityFitboundsCameraBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class FitBoundsCameraActivity :
    BaseActivity<ActivityFitboundsCameraBinding>(R.layout.activity_fitbounds_camera),
    OnMapReadyCallback {

    private lateinit var mMap: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        mMap = ktmap

        binding.buttonFitBoundsBusan.setOnClickListener {

            ktmap.apply {
                addOverlay(
                    PolygonOverlayOptions.Builder().apply {
                        coords(POLYGON_OVERLAY_COORDS)
                        color(Color.RED)
                        opacity(0.3f)
                    }.build()
                )

                flyTo(
                    cameraOptions = CameraBoundsOptions(
                        bounds =
                        LngLatBounds.fromLngLats(
                            listOf(
                                LngLat(128.7384361, 34.8799083),
                                LngLat(129.3728194, 35.3959361)
                            )
                        )
                    ),
                    duration = 1000
                )
            }
        }
    }

    companion object {
        private val POLYGON_OVERLAY_COORDS = listOf(
            LngLat(128.7384361, 34.8799083),
            LngLat(129.3728194, 34.8799083),
            LngLat(129.3728194, 35.3959361),
            LngLat(128.7384361, 35.3959361)
        )
    }
}