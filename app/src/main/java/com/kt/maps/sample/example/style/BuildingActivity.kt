package com.kt.maps.sample.example.style

import android.graphics.Color
import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityBuildingBinding

class BuildingActivity :
    BaseActivity<ActivityBuildingBinding>(R.layout.activity_building), OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.buildingMap.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@BuildingActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.jumpTo(
            cameraOptions = CameraPositionOptions(
                pitch = 60f,
                bearing = 90f,
                zoom = 16f,
                // 잠실역
                lngLat = LngLat(127.10016448695572, 37.51329170850403)
            )
        )

        initBuildingLayerGroup()

        binding.run {
            // 건물 레이어 enable/disable
            buildingEnable.setOnCheckedChangeListener { _, isChecked ->
                map.buildingLayerGroup.enabled = isChecked
            }

            // 건물 높이 비율 변경
            heightSlider.addOnChangeListener { _, value, _ ->
                map.buildingLayerGroup.heightRatio = value
            }

            // 건물면 투명도 변경
            opacitySlider.addOnChangeListener { _, value, _ ->
                map.buildingLayerGroup.opacity = value
            }

            // 건물면 색상 변경
            colorRSlider.addOnChangeListener { _, _, _ ->
                updateBuildingColor()
            }
            colorGSlider.addOnChangeListener { _, _, _ ->
                updateBuildingColor()
            }
            colorBSlider.addOnChangeListener { _, _, _ ->
                updateBuildingColor()
            }
        }
    }

    private fun initBuildingLayerGroup() {
        // 건물 레이어 enable/disable
        binding.run {
            buildingEnable.isActivated = map.buildingLayerGroup.enabled

            // 건물 높이 변경
            heightSlider.value = map.buildingLayerGroup.heightRatio

            // 건물면 투명도
            opacitySlider.value = map.buildingLayerGroup.opacity

            // 건물면 색상
            colorRSlider.value = Color.red(map.buildingLayerGroup.color).toFloat()
            colorGSlider.value = Color.green(map.buildingLayerGroup.color).toFloat()
            colorBSlider.value = Color.blue(map.buildingLayerGroup.color).toFloat()
        }
    }

    private fun updateBuildingColor() {
        binding.run {
            val r = colorRSlider.value.toInt()
            val g = colorGSlider.value.toInt()
            val b = colorBSlider.value.toInt()

            map.buildingLayerGroup.color = Color.rgb(r, g, b)
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
}