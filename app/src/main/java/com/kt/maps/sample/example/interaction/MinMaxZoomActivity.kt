package com.kt.maps.sample.example.interaction

import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.OnCameraChangeListener
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityMinMaxZoomBinding

class MinMaxZoomActivity : BaseActivity<ActivityMinMaxZoomBinding>(R.layout.activity_min_max_zoom),
    OnMapReadyCallback {

    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@MinMaxZoomActivity)

            // 지도 최소/최대 줌 레벨 제한 설정
            mapOptions.minZoom(9f)
            mapOptions.maxZoom(15f)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap.apply {
            // 지도 카메라 변경에 대한 리스너를 등록한다.
            addOnCameraChangedListener(
                object : OnCameraChangeListener {
                    override fun onCameraMoveStarted(reason: OnCameraChangeListener.REASON) {
                        binding.zoomLevelText.text =
                            getString(R.string.format_double, map.getCameraPosition().zoom)
                    }

                    override fun onCameraMoveCanceled() {
                        binding.zoomLevelText.text =
                            getString(R.string.format_double, map.getCameraPosition().zoom)
                    }

                    override fun onCameraMove() {
                        binding.zoomLevelText.text =
                            getString(R.string.format_double, map.getCameraPosition().zoom)
                    }
                }
            )
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