package com.kt.maps.sample.example.animations

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAnimateMapCameraBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback


class AnimateMapCameraActivity :
    BaseActivity<ActivityAnimateMapCameraBinding>(R.layout.activity_animate_map_camera),
    OnMapReadyCallback {
    private lateinit var map: KtMap

    private var animator: ValueAnimator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {

            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 17.0,
                    pitch = 60.0,
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
                        cameraOptions = CameraPositionOptions().bearing((updateValue).toDouble())
                    )
                }
                duration = 20000
                start()
            }

        }
    }

}