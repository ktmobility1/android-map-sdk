package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityRandomCameraBinding
import com.kt.maps.sample.ui.common.showSnackbar

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

        // 줌 변경 버튼 탭 핸들러
        binding.buttonCameraZoom.setOnClickListener {
            val zoom = random(6f, 20f) // 6과 20 사이 랜덤 숫자 생성

            map.easeTo(
                cameraOptions = CameraPositionOptions().zoom(zoom),
                duration = 3000
            )
            mapView.showSnackbar(R.string.changed_camera_zoom, zoom)
        }

        // 중심점  변경 버튼 탭 핸들러
        binding.buttonCameraCenter.setOnClickListener {
            val lng = random(127f, 128f) // 경도 - 127과 128 사이 랜덤 숫자 생성
            val lat = random(35f, 37f) // 위도 - 35와 37 사이 랜덤 숫자 생성

            map.jumpTo(
                CameraPositionOptions().lngLat(
                    LngLat(
                        latitude = lat.toDouble(),
                        longitude = lng.toDouble()
                    )
                )
            )

            mapView.showSnackbar(R.string.changed_camera_center, lat, lng)
        }

    }

    private fun random(min: Float, max: Float) = (Math.random() * (max - min) + min).toFloat()

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