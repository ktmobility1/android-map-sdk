package com.kt.maps.sample.example.animations

import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.graphics.PointF
import android.os.Bundle
import com.kt.maps.camera.CameraPositionOptions
import com.kt.maps.geometry.LngLat
import com.kt.maps.gesture.OnMapTapListener
import com.kt.maps.gesture.addOnMapTapListener
import com.kt.maps.overlay.marker.Marker
import com.kt.maps.overlay.marker.MarkerOptions
import com.kt.maps.sample.BaseActivity
import com.kt.maps.sample.R
import com.kt.maps.sample.databinding.ActivityAnimateMarkerBinding
import com.kt.maps.sdk.KtMap
import com.kt.maps.sdk.OnMapReadyCallback

class AnimateMarkerActivity :
    BaseActivity<ActivityAnimateMarkerBinding>(R.layout.activity_animate_marker),
    OnMapReadyCallback, OnMapTapListener {
    private lateinit var map: KtMap

    private var animator: ValueAnimator? = null
    private var currentPoint = LngLat(longitude = 127.02881, latitude = 37.47195)
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mapView = binding.map
        mapView.getMapAsync(this)
    }

    override fun onMapReady(ktmap: KtMap) {
        map = ktmap

        map.apply {

            addOnMapTapListener(this@AnimateMarkerActivity)

            jumpTo(cameraOptions = CameraPositionOptions().zoom(16.0).lngLat(currentPoint))

            marker = addOverlay(
                MarkerOptions.Builder().apply {
                    position(currentPoint)
                    title("Marker 1")
                    snippet("${currentPoint.longitude}, ${currentPoint.latitude}")
                }.build()
            )

        }
    }

    override fun onTap(point: PointF, lngLat: LngLat): Boolean {
        animator?.let {
            if (it.isStarted) {
                currentPoint = it.animatedValue as LngLat
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
            setObjectValues(currentPoint, lngLat)
            setEvaluator(pointEvaluator)
            addUpdateListener {
                val updateValue = it.animatedValue as LngLat
                marker?.position = LngLat(updateValue.longitude, updateValue.latitude)
            }
            duration = 1000
            start()
        }

        currentPoint = lngLat

        return true
    }
}