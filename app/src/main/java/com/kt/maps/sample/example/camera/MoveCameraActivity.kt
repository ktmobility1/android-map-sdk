package com.kt.maps.sample.example.camera

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMoveCameraBinding
import com.kt.maps.sample.ui.common.showSnackbar

class MoveCameraActivity :
    BaseActivity<ActivityMoveCameraBinding>(R.layout.activity_move_camera),
    OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    companion object {
        // 서울
        val START_OPTIONS = CameraPositionOptions(lngLat = LngLat(126.9778, 37.5722), zoom = 11f)
        // 인천 공항
        val END_OPTIONS = CameraPositionOptions(lngLat = LngLat(126.4513, 37.4493), zoom = 11f)
    }

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private var selectedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MoveCameraActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        initMap()
        initSpinner()
        initFab()
    }

    /**
     * 지도 초기 위치 이동
     */
    private fun initMap() {
        map.jumpTo(cameraOptions = START_OPTIONS)
    }

    /**
     * 지도 이동 버튼 초기화
     */
    private fun initFab() {
        binding.buttonCameraMove.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                initMap()
                mapView.showSnackbar("camera init")
            } else {
                // 지도 이동 animation type에 따라 지도를 이동시킨다.
                when (selectedIndex) {
                    0 -> map.jumpTo(cameraOptions = END_OPTIONS)
                    1 -> map.easeTo(cameraOptions = END_OPTIONS, duration = 3000)
                    2 -> map.flyTo(cameraOptions = END_OPTIONS, duration = 3000)
                }
                mapView.showSnackbar("camera ${resources.getStringArray(R.array.camera_animation_type_array)[selectedIndex]}")
            }
        }
    }

    /**
     * 지도 이동 animation type 초기화
     */
    private fun initSpinner() {
        // 지도 이동 animation 선택을 위한 Spinner
        binding.spinnerCameraType.apply {
            // 지도 이동 animation item 설정
            adapter = ArrayAdapter.createFromResource(
                this@MoveCameraActivity,
                R.array.camera_animation_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            // 지도 이동 animation item 기본값 반영
            setSelection(selectedIndex)

            // spinner item 선택시 animation type 변경
            onItemSelectedListener = this@MoveCameraActivity
        }
    }

    /**
     * 지도 이동 animation type 선택
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // 지도 이동 animation type 변경
        selectedIndex = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

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