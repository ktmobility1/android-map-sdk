package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityRandomCameraBinding
import com.kt.maps.sample.ui.common.showSnackbar
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback

class RandomCameraActivity :
    BaseActivity<ActivityRandomCameraBinding>(R.layout.activity_random_camera), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@RandomCameraActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        // 줌 변경 버튼 클릭 핸들러
        binding.buttonCameraZoom.setOnClickListener {
            val zoom = random(6.0, 20.0) // 6과 20 사이 랜덤 숫자 생성

            map.easeTo(
                cameraOptions = CameraPositionOptions().zoom(zoom),
                duration = 3000
            )
            mapView.showSnackbar(R.string.changed_camera_zoom, zoom)
        }

        // 중심점  변경 버튼 클릭 핸들러
        binding.buttonCameraCenter.setOnClickListener {
            val lng = random(127.0, 128.0) // 경도 - 127과 128 사이 랜덤 숫자 생성
            val lat = random(35.0, 37.0) // 위도 - 35와 37 사이 랜덤 숫자 생성

            map.jumpTo(CameraPositionOptions().lngLat(LngLat(latitude = lat, longitude = lng)))

            mapView.showSnackbar(R.string.changed_camera_center, lat, lng)
        }

    }

    private fun random(min: Double, max: Double) = Math.random() * (max - min) + min

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