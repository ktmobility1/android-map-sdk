package com.kt.maps.sample.example.animations

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAnimateMapCameraBinding


class AnimateMapCameraActivity :
    BaseActivity<ActivityAnimateMapCameraBinding>(R.layout.activity_animate_map_camera),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var mapView: MapView

    private var animator: ValueAnimator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {

            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 17f,
                    pitch = 60f,
                    lngLat = LngLat(longitude = 127.029414, latitude = 37.471401)
                )
            )

            animator = ValueAnimator().apply {
                setObjectValues(0f, 360f)
                interpolator = LinearInterpolator()
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    val updateValue = it.animatedValue as Float
                    map.jumpTo(
                        cameraOptions = CameraPositionOptions().bearing((updateValue))
                    )
                }
                duration = 20000
                start()
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