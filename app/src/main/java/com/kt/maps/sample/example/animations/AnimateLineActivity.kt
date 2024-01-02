package com.kt.maps.sample.example.animations

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import com.kt.maps.KtMap
import com.kt.maps.MapView
import com.kt.maps.OnMapReadyCallback
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAnimateLineBinding


class AnimateLineActivity :
    BaseActivity<ActivityAnimateLineBinding>(R.layout.activity_animate_line),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private var animator: ValueAnimator? = null
    private val lngLats = mutableListOf<LngLat>()

    // kt연구개발센터 사옥
    private var current = LngLat(127.029414, 37.471401)

    // kt광화문 사옥
    private val destination = LngLat(126.978916, 37.572020)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView = binding.map.apply {
            onCreate(savedInstanceState)
            getMapAsync(this@AnimateLineActivity)
        }
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {
            jumpTo(
                cameraOptions = CameraPositionOptions(
                    zoom = 11.5f,
                    lngLat = LngLat(longitude = 127.004165, latitude = 37.5217105)
                )
            )

            animator?.let {
                if (it.isStarted) {
                    current = it.animatedValue as LngLat
                    it.cancel()
                }
            }

            val pointEvaluator = TypeEvaluator<LngLat> { fraction, startValue, endValue ->
                LngLat(
                    startValue.longitude + fraction * (endValue.longitude - startValue.longitude),
                    startValue.latitude + fraction * (endValue.latitude - startValue.latitude)
                )
            }

            animator = ValueAnimator().apply {
                setObjectValues(current, destination)
                setEvaluator(pointEvaluator)
                addUpdateListener {
                    val updateValue = it.animatedValue as LngLat

                    lngLats.add(updateValue)

                    map.addOverlay(PolylineOverlayOptions.Builder().apply {
                        lngLats(lngLats)
                        color(Color.RED)
                        width(10f)
                        opacity(0.2f)
                    }.build())
                }
                duration = 4000
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