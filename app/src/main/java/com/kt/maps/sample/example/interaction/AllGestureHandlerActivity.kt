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

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@AllGestureHandlerActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            // 모든 지도 제스쳐 활성화 허용
            gestureSettings.allGestureEnabled = true
        }

        // 사용자 이벤트(move,pitch,rotate) 발생 시 지도에 제스쳐를 활성화할 지 여부를 처리 하기 위한 리스너 등록
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