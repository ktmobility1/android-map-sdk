package com.kt.maps.sample.example.camera

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAnimatedCameraBinding
import com.kt.maps.sample.ui.common.showToast
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class AnimatedCameraActivity :
    BaseActivity<ActivityAnimatedCameraBinding>(R.layout.activity_animated_camera), OnMapReadyCallback {

    private lateinit var mMap: KtMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        mMap = ktmap

        binding.buttonCameraEaseTo.setOnClickListener {
            mMap.jumpTo(cameraOptions = CameraPositionOptions(lngLat = START, zoom = START_ZOOM))
            mMap.easeTo(
                cameraOptions = CameraPositionOptions(lngLat = END, zoom = END_ZOOM), duration = 3000
            )
            baseContext.showToast("camera ease animation")
        }

        binding.buttonCameraFlyTo.setOnClickListener {
            mMap.jumpTo(cameraOptions = CameraPositionOptions(lngLat = START, zoom = START_ZOOM))
            mMap.flyTo(
                cameraOptions = CameraPositionOptions(lngLat = END, zoom = END_ZOOM), duration = 3000
            )
            baseContext.showToast("camera fly animation")
        }

    }

    companion object {
        val START = LngLat(126.97783375071614,37.57220405741807)
        val END = LngLat(129.07298003630683,35.18022495260142)
        const val START_ZOOM = 6.0
        const val END_ZOOM = 14.0
    }
}