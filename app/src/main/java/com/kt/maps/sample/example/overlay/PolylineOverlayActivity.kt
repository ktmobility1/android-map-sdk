package com.kt.maps.sample.example.overlay

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityPolylineOverlayBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class PolylineOverlayActivity :
    BaseActivity<ActivityPolylineOverlayBinding>(R.layout.activity_polyline_overlay),
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
                PolylineOverlayOptions.Builder().apply {
                    coords(POLYLINE_OVERLAY_COORDS)
                    color(Color.BLUE)
                    width(10f)
                    opacity(0.2f)
                }.build()
            )


        }

    }

    companion object {
        val POLYLINE_OVERLAY_COORDS = listOf(
            LngLat(latitude = 37.57341, longitude = 126.97877),
            LngLat(latitude = 37.57181, longitude = 126.97519),
            LngLat(latitude = 37.57050, longitude = 126.97923),
            LngLat(latitude = 37.56852, longitude = 126.97621),
            LngLat(latitude = 37.56800, longitude = 126.97899)
        )
    }

}