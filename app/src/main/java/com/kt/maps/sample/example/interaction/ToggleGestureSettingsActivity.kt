package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.control.location.currentLocation
import com.kt.maps.control.zoom.zoomControls
import com.kt.maps.gesture.gestureSettings
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityToggleGestureSettingsBinding

class ToggleGestureSettingsActivity :
    BaseActivity<ActivityToggleGestureSettingsBinding>(R.layout.activity_toggle_gesture_settings),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@ToggleGestureSettingsActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            currentLocation.enabled = false
            zoomControls.enabled = false
        }

        // toggle 버튼 제스쳐 활성화 여부 리스너 등록
        binding.run {
            zoomToggle.setOnCheckedChangeListener { _, isChecked ->
                // 지도 줌 제스쳐 활성화 여부
                ktmap.gestureSettings.zoomEnabled = isChecked
            }

            pitchToggle.setOnCheckedChangeListener { _, isChecked ->
                // 지도 기울기 제스쳐 활성화 여부
                ktmap.gestureSettings.pitchEnabled = isChecked
            }

            rotateToggle.setOnCheckedChangeListener { _, isChecked ->
                // 지도 회전 제스쳐 활성화 여부
                ktmap.gestureSettings.rotateEnabled = isChecked
            }

            panToggle.setOnCheckedChangeListener { _, isChecked ->
                // 지도 이동 제스쳐 활성화 여부
                ktmap.gestureSettings.panEnabled = isChecked
            }

            horizontalPanToggle.setOnCheckedChangeListener { _, isChecked ->
                // 지도 가로 방향 이동 제스쳐 활성화 여부
                ktmap.gestureSettings.horizontalPanEnabled = isChecked
            }
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