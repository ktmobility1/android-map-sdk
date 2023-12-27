package com.kt.maps.sample.example.camera

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraBoundsOptions
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.geometry.LngLatBounds
import com.kt.maps.overlay.polygon.PolygonOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityFitboundsCameraBinding
import com.kt.maps.sample.ui.common.showSnackbar

class FitBoundsCameraActivity :
    BaseActivity<ActivityFitboundsCameraBinding>(R.layout.activity_fitbounds_camera),
    OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    companion object {
        // 인천 공항
        val START_OPTIONS = CameraPositionOptions(lngLat = LngLat(126.4513, 37.4493), zoom = 11f)

        val boundsMap = mapOf(
            "seoul" to listOf(
                LngLat(126.734086, 37.413294),
                LngLat(127.269311, 37.413294),
                LngLat(127.269311, 37.715133),
                LngLat(126.734086, 37.715133)
            ),
            "busan" to listOf(
                LngLat(128.7384361, 34.8799083),
                LngLat(129.3728194, 34.8799083),
                LngLat(129.3728194, 35.3959361),
                LngLat(128.7384361, 35.3959361)
            )
        )

        val colorMap = mapOf(
            "seoul" to Color.RED,
            "busan" to Color.BLUE
        )
    }

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private var selectedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@FitBoundsCameraActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        initMap()
        initSpinner()
        initFab()
    }

    private fun color(key: String): Int = colorMap[key] ?: Color.GRAY

    /**
     * 지도 설정 초기화
     */
    private fun initMap() {
        map.apply {
            // 지도 영역를 표시하기 위한 Polygon 생성
            boundsMap.forEach {
                addOverlay(
                    PolygonOverlayOptions.Builder().apply {
                        lngLats(it.value)
                        color(color(it.key))
                        opacity(0.3f)
                    }.build()
                )
            }
            moveInitMap()
        }
    }

    /**
     * 지도 위치 초기화
     */
    private fun moveInitMap() {
        map.jumpTo(cameraOptions = START_OPTIONS)
    }

    /**
     * 지도 영역 이동 하기 위한 버튼 초기화
     */
    private fun initFab() {
        binding.buttonCameraMove.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                moveInitMap()
                mapView.showSnackbar("camera init")
            } else {
                val key = resources.getStringArray(R.array.fit_bounds_array)[selectedIndex]

                if (boundsMap.containsKey(key)) {
                    boundsMap[key]?.let { lngLats ->
                        map.flyTo(
                            cameraOptions = CameraBoundsOptions(
                                bounds = LngLatBounds.fromLngLats(lngLats)
                            )
                        )
                    }
                }
                mapView.showSnackbar("camera bounds: $key")
            }
        }
    }

    /**
     * 지도 영역 이동 초기화
     */
    private fun initSpinner() {
        // 지도 이동 animation 선택을 위한 Spinner
        binding.fitboundsSpinner.apply {
            // 지도 이동 animation item 설정
            adapter = ArrayAdapter.createFromResource(
                this@FitBoundsCameraActivity,
                R.array.fit_bounds_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            // 지도 이동 animation item 기본값 반영
            setSelection(selectedIndex)

            // spinner item 선택시 animation type 변경
            onItemSelectedListener = this@FitBoundsCameraActivity
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