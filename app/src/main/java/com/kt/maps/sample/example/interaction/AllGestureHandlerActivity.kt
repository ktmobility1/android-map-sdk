package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.gesture.gestureSettings
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAllGestureHandlerBinding

class AllGestureHandlerActivity :
    BaseActivity<ActivityAllGestureHandlerBinding>(R.layout.activity_all_gesture_handler),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.gestureSettings.allGestureEnabled = true

        // 사용자 이벤트(move,tilt,rotate) 발생 시 맵에 활성화 시킬지 여부
        // true는 활성화 false는 비활성화 상태입니다.
        binding.interactiveButton.setOnCheckedChangeListener { _, isChecked ->
            map.gestureSettings.allGestureEnabled = isChecked
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