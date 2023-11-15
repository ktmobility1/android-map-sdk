package com.kt.maps.sample.example.animations

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.overlay.polyline.PolylineOverlayOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAnimateLineBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.MapView
import com.kt.maps.sdk.OnMapReadyCallback


class AnimateLineActivity :
    BaseActivity<ActivityAnimateLineBinding>(R.layout.activity_animate_line),
    OnMapReadyCallback {
    private lateinit var map: KtMap
    private lateinit var mapView: MapView
    private var animator: ValueAnimator? = null
    val POLYLINE_OVERLAY_COORDS = mutableListOf<LngLat>()

    // kt연구개발센터 사옥
    private var current = LngLat(127.029414, 37.471401)

    // kt광화문 사옥
    private var destination = LngLat(126.978916, 37.572020)


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
                    zoom = 11.5,
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

                    POLYLINE_OVERLAY_COORDS.add(updateValue)

                    map.addOverlay(PolylineOverlayOptions.Builder().apply {
                        coords(POLYLINE_OVERLAY_COORDS)
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

}